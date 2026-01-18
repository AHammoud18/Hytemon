/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @CheckReturnValue
/*      */ final class MessageSchema<T>
/*      */   implements Schema<T>
/*      */ {
/*      */   private static final int INTS_PER_FIELD = 3;
/*      */   private static final int OFFSET_BITS = 20;
/*      */   private static final int OFFSET_MASK = 1048575;
/*      */   private static final int FIELD_TYPE_MASK = 267386880;
/*      */   private static final int REQUIRED_MASK = 268435456;
/*      */   private static final int ENFORCE_UTF8_MASK = 536870912;
/*      */   private static final int LEGACY_ENUM_IS_CLOSED_MASK = -2147483648;
/*      */   private static final int NO_PRESENCE_SENTINEL = 1048575;
/*   73 */   private static final int[] EMPTY_INT_ARRAY = new int[0];
/*      */ 
/*      */   
/*      */   private static final int REQUIRED_BIT = 256;
/*      */ 
/*      */   
/*      */   private static final int UTF8_CHECK_BIT = 512;
/*      */ 
/*      */   
/*      */   private static final int CHECK_INITIALIZED_BIT = 1024;
/*      */ 
/*      */   
/*      */   private static final int LEGACY_ENUM_IS_CLOSED_BIT = 2048;
/*      */ 
/*      */   
/*      */   private static final int HAS_HAS_BIT = 4096;
/*      */ 
/*      */   
/*      */   static final int ONEOF_TYPE_OFFSET = 51;
/*      */   
/*   93 */   private static final Unsafe UNSAFE = UnsafeUtil.getUnsafe();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[] buffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Object[] objects;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int minFieldNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int maxFieldNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MessageLite defaultInstance;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean hasExtensions;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean lite;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean useCachedSizeField;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int[] intArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int checkInitializedCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int repeatedFieldOffsetStart;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final NewInstanceSchema newInstanceSchema;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ListFieldSchema listFieldSchema;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final UnknownFieldSchema<?, ?> unknownFieldSchema;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ExtensionSchema<?> extensionSchema;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MapFieldSchema mapFieldSchema;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MessageSchema(int[] buffer, Object[] objects, int minFieldNumber, int maxFieldNumber, MessageLite defaultInstance, boolean useCachedSizeField, int[] intArray, int checkInitialized, int mapFieldPositions, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
/*  194 */     this.buffer = buffer;
/*  195 */     this.objects = objects;
/*  196 */     this.minFieldNumber = minFieldNumber;
/*  197 */     this.maxFieldNumber = maxFieldNumber;
/*      */     
/*  199 */     this.lite = defaultInstance instanceof GeneratedMessageLite;
/*  200 */     this.hasExtensions = (extensionSchema != null && extensionSchema.hasExtensions(defaultInstance));
/*  201 */     this.useCachedSizeField = useCachedSizeField;
/*      */     
/*  203 */     this.intArray = intArray;
/*  204 */     this.checkInitializedCount = checkInitialized;
/*  205 */     this.repeatedFieldOffsetStart = mapFieldPositions;
/*      */     
/*  207 */     this.newInstanceSchema = newInstanceSchema;
/*  208 */     this.listFieldSchema = listFieldSchema;
/*  209 */     this.unknownFieldSchema = unknownFieldSchema;
/*  210 */     this.extensionSchema = extensionSchema;
/*  211 */     this.defaultInstance = defaultInstance;
/*  212 */     this.mapFieldSchema = mapFieldSchema;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> MessageSchema<T> newSchema(Class<T> messageClass, MessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
/*  223 */     if (messageInfo instanceof RawMessageInfo) {
/*  224 */       return newSchemaForRawMessageInfo((RawMessageInfo)messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  233 */     return newSchemaForMessageInfo((StructuralMessageInfo)messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> MessageSchema<T> newSchemaForRawMessageInfo(RawMessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
/*      */     int oneofCount, minFieldNumber, maxFieldNumber, numEntries, mapFieldCount, checkInitialized, intArray[], objectsPosition;
/*  250 */     String info = messageInfo.getStringInfo();
/*  251 */     int length = info.length();
/*  252 */     int i = 0;
/*      */     
/*  254 */     int next = info.charAt(i++);
/*  255 */     if (next >= 55296) {
/*  256 */       int result = next & 0x1FFF;
/*  257 */       int shift = 13;
/*  258 */       while ((next = info.charAt(i++)) >= 55296) {
/*  259 */         result |= (next & 0x1FFF) << shift;
/*  260 */         shift += 13;
/*      */       } 
/*  262 */       next = result | next << shift;
/*      */     } 
/*  264 */     int unusedFlags = next;
/*      */     
/*  266 */     next = info.charAt(i++);
/*  267 */     if (next >= 55296) {
/*  268 */       int result = next & 0x1FFF;
/*  269 */       int shift = 13;
/*  270 */       while ((next = info.charAt(i++)) >= 55296) {
/*  271 */         result |= (next & 0x1FFF) << shift;
/*  272 */         shift += 13;
/*      */       } 
/*  274 */       next = result | next << shift;
/*      */     } 
/*  276 */     int fieldCount = next;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  288 */     if (fieldCount == 0) {
/*  289 */       oneofCount = 0;
/*  290 */       int hasBitsCount = 0;
/*  291 */       minFieldNumber = 0;
/*  292 */       maxFieldNumber = 0;
/*  293 */       numEntries = 0;
/*  294 */       mapFieldCount = 0;
/*  295 */       int repeatedFieldCount = 0;
/*  296 */       checkInitialized = 0;
/*  297 */       intArray = EMPTY_INT_ARRAY;
/*  298 */       objectsPosition = 0;
/*      */     } else {
/*  300 */       next = info.charAt(i++);
/*  301 */       if (next >= 55296) {
/*  302 */         int result = next & 0x1FFF;
/*  303 */         int shift = 13;
/*  304 */         while ((next = info.charAt(i++)) >= 55296) {
/*  305 */           result |= (next & 0x1FFF) << shift;
/*  306 */           shift += 13;
/*      */         } 
/*  308 */         next = result | next << shift;
/*      */       } 
/*  310 */       oneofCount = next;
/*      */       
/*  312 */       next = info.charAt(i++);
/*  313 */       if (next >= 55296) {
/*  314 */         int result = next & 0x1FFF;
/*  315 */         int shift = 13;
/*  316 */         while ((next = info.charAt(i++)) >= 55296) {
/*  317 */           result |= (next & 0x1FFF) << shift;
/*  318 */           shift += 13;
/*      */         } 
/*  320 */         next = result | next << shift;
/*      */       } 
/*  322 */       int hasBitsCount = next;
/*      */       
/*  324 */       next = info.charAt(i++);
/*  325 */       if (next >= 55296) {
/*  326 */         int result = next & 0x1FFF;
/*  327 */         int shift = 13;
/*  328 */         while ((next = info.charAt(i++)) >= 55296) {
/*  329 */           result |= (next & 0x1FFF) << shift;
/*  330 */           shift += 13;
/*      */         } 
/*  332 */         next = result | next << shift;
/*      */       } 
/*  334 */       minFieldNumber = next;
/*      */       
/*  336 */       next = info.charAt(i++);
/*  337 */       if (next >= 55296) {
/*  338 */         int result = next & 0x1FFF;
/*  339 */         int shift = 13;
/*  340 */         while ((next = info.charAt(i++)) >= 55296) {
/*  341 */           result |= (next & 0x1FFF) << shift;
/*  342 */           shift += 13;
/*      */         } 
/*  344 */         next = result | next << shift;
/*      */       } 
/*  346 */       maxFieldNumber = next;
/*      */       
/*  348 */       next = info.charAt(i++);
/*  349 */       if (next >= 55296) {
/*  350 */         int result = next & 0x1FFF;
/*  351 */         int shift = 13;
/*  352 */         while ((next = info.charAt(i++)) >= 55296) {
/*  353 */           result |= (next & 0x1FFF) << shift;
/*  354 */           shift += 13;
/*      */         } 
/*  356 */         next = result | next << shift;
/*      */       } 
/*  358 */       numEntries = next;
/*      */       
/*  360 */       next = info.charAt(i++);
/*  361 */       if (next >= 55296) {
/*  362 */         int result = next & 0x1FFF;
/*  363 */         int shift = 13;
/*  364 */         while ((next = info.charAt(i++)) >= 55296) {
/*  365 */           result |= (next & 0x1FFF) << shift;
/*  366 */           shift += 13;
/*      */         } 
/*  368 */         next = result | next << shift;
/*      */       } 
/*  370 */       mapFieldCount = next;
/*      */       
/*  372 */       next = info.charAt(i++);
/*  373 */       if (next >= 55296) {
/*  374 */         int result = next & 0x1FFF;
/*  375 */         int shift = 13;
/*  376 */         while ((next = info.charAt(i++)) >= 55296) {
/*  377 */           result |= (next & 0x1FFF) << shift;
/*  378 */           shift += 13;
/*      */         } 
/*  380 */         next = result | next << shift;
/*      */       } 
/*  382 */       int repeatedFieldCount = next;
/*      */       
/*  384 */       next = info.charAt(i++);
/*  385 */       if (next >= 55296) {
/*  386 */         int result = next & 0x1FFF;
/*  387 */         int shift = 13;
/*  388 */         while ((next = info.charAt(i++)) >= 55296) {
/*  389 */           result |= (next & 0x1FFF) << shift;
/*  390 */           shift += 13;
/*      */         } 
/*  392 */         next = result | next << shift;
/*      */       } 
/*  394 */       checkInitialized = next;
/*  395 */       intArray = new int[checkInitialized + mapFieldCount + repeatedFieldCount];
/*      */       
/*  397 */       objectsPosition = oneofCount * 2 + hasBitsCount;
/*      */     } 
/*      */     
/*  400 */     Unsafe unsafe = UNSAFE;
/*  401 */     Object[] messageInfoObjects = messageInfo.getObjects();
/*  402 */     int checkInitializedPosition = 0;
/*  403 */     Class<?> messageClass = messageInfo.getDefaultInstance().getClass();
/*  404 */     int[] buffer = new int[numEntries * 3];
/*  405 */     Object[] objects = new Object[numEntries * 2];
/*      */     
/*  407 */     int mapFieldIndex = checkInitialized;
/*  408 */     int repeatedFieldIndex = checkInitialized + mapFieldCount;
/*      */     
/*  410 */     int bufferIndex = 0;
/*  411 */     while (i < length) {
/*      */       int fieldOffset, presenceMaskShift, presenceFieldOffset;
/*      */ 
/*      */ 
/*      */       
/*  416 */       next = info.charAt(i++);
/*  417 */       if (next >= 55296) {
/*  418 */         int result = next & 0x1FFF;
/*  419 */         int shift = 13;
/*  420 */         while ((next = info.charAt(i++)) >= 55296) {
/*  421 */           result |= (next & 0x1FFF) << shift;
/*  422 */           shift += 13;
/*      */         } 
/*  424 */         next = result | next << shift;
/*      */       } 
/*  426 */       int fieldNumber = next;
/*      */       
/*  428 */       next = info.charAt(i++);
/*  429 */       if (next >= 55296) {
/*  430 */         int result = next & 0x1FFF;
/*  431 */         int shift = 13;
/*  432 */         while ((next = info.charAt(i++)) >= 55296) {
/*  433 */           result |= (next & 0x1FFF) << shift;
/*  434 */           shift += 13;
/*      */         } 
/*  436 */         next = result | next << shift;
/*      */       } 
/*  438 */       int fieldTypeWithExtraBits = next;
/*  439 */       int fieldType = fieldTypeWithExtraBits & 0xFF;
/*      */       
/*  441 */       if ((fieldTypeWithExtraBits & 0x400) != 0) {
/*  442 */         intArray[checkInitializedPosition++] = bufferIndex;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  450 */       if (fieldType >= 51) {
/*  451 */         Field oneofField, oneofCaseField; next = info.charAt(i++);
/*  452 */         if (next >= 55296) {
/*  453 */           int result = next & 0x1FFF;
/*  454 */           int shift = 13;
/*  455 */           while ((next = info.charAt(i++)) >= 55296) {
/*  456 */             result |= (next & 0x1FFF) << shift;
/*  457 */             shift += 13;
/*      */           } 
/*  459 */           next = result | next << shift;
/*      */         } 
/*  461 */         int oneofIndex = next;
/*      */         
/*  463 */         int oneofFieldType = fieldType - 51;
/*  464 */         if (oneofFieldType == 9 || oneofFieldType == 17) {
/*      */           
/*  466 */           objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
/*  467 */         } else if (oneofFieldType == 12) {
/*      */ 
/*      */           
/*  470 */           if (messageInfo.getSyntax().equals(ProtoSyntax.PROTO2) || (fieldTypeWithExtraBits & 0x800) != 0)
/*      */           {
/*  472 */             objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  477 */         int index = oneofIndex * 2;
/*  478 */         Object o = messageInfoObjects[index];
/*  479 */         if (o instanceof Field) {
/*  480 */           oneofField = (Field)o;
/*      */         } else {
/*  482 */           oneofField = reflectField(messageClass, (String)o);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  487 */           messageInfoObjects[index] = oneofField;
/*      */         } 
/*      */         
/*  490 */         fieldOffset = (int)unsafe.objectFieldOffset(oneofField);
/*      */ 
/*      */         
/*  493 */         index++;
/*  494 */         o = messageInfoObjects[index];
/*  495 */         if (o instanceof Field) {
/*  496 */           oneofCaseField = (Field)o;
/*      */         } else {
/*  498 */           oneofCaseField = reflectField(messageClass, (String)o);
/*  499 */           messageInfoObjects[index] = oneofCaseField;
/*      */         } 
/*      */         
/*  502 */         presenceFieldOffset = (int)unsafe.objectFieldOffset(oneofCaseField);
/*  503 */         presenceMaskShift = 0;
/*      */       } else {
/*  505 */         Field field = reflectField(messageClass, (String)messageInfoObjects[objectsPosition++]);
/*  506 */         if (fieldType == 9 || fieldType == 17) {
/*  507 */           objects[bufferIndex / 3 * 2 + 1] = field.getType();
/*  508 */         } else if (fieldType == 27 || fieldType == 49) {
/*      */           
/*  510 */           objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
/*  511 */         } else if (fieldType == 12 || fieldType == 30 || fieldType == 44) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  516 */           if (messageInfo.getSyntax() == ProtoSyntax.PROTO2 || (fieldTypeWithExtraBits & 0x800) != 0)
/*      */           {
/*  518 */             objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
/*      */           }
/*  520 */         } else if (fieldType == 50) {
/*  521 */           intArray[mapFieldIndex++] = bufferIndex;
/*  522 */           objects[bufferIndex / 3 * 2] = messageInfoObjects[objectsPosition++];
/*  523 */           if ((fieldTypeWithExtraBits & 0x800) != 0) {
/*  524 */             objects[bufferIndex / 3 * 2 + 1] = messageInfoObjects[objectsPosition++];
/*      */           }
/*      */         } 
/*      */         
/*  528 */         fieldOffset = (int)unsafe.objectFieldOffset(field);
/*  529 */         boolean hasHasBit = ((fieldTypeWithExtraBits & 0x1000) != 0);
/*  530 */         if (hasHasBit && fieldType <= 17) {
/*  531 */           Field hasBitsField; next = info.charAt(i++);
/*  532 */           if (next >= 55296) {
/*  533 */             int result = next & 0x1FFF;
/*  534 */             int shift = 13;
/*  535 */             while ((next = info.charAt(i++)) >= 55296) {
/*  536 */               result |= (next & 0x1FFF) << shift;
/*  537 */               shift += 13;
/*      */             } 
/*  539 */             next = result | next << shift;
/*      */           } 
/*  541 */           int hasBitsIndex = next;
/*      */ 
/*      */           
/*  544 */           int index = oneofCount * 2 + hasBitsIndex / 32;
/*  545 */           Object o = messageInfoObjects[index];
/*  546 */           if (o instanceof Field) {
/*  547 */             hasBitsField = (Field)o;
/*      */           } else {
/*  549 */             hasBitsField = reflectField(messageClass, (String)o);
/*  550 */             messageInfoObjects[index] = hasBitsField;
/*      */           } 
/*      */           
/*  553 */           presenceFieldOffset = (int)unsafe.objectFieldOffset(hasBitsField);
/*  554 */           presenceMaskShift = hasBitsIndex % 32;
/*      */         } else {
/*  556 */           presenceFieldOffset = 1048575;
/*  557 */           presenceMaskShift = 0;
/*      */         } 
/*      */         
/*  560 */         if (fieldType >= 18 && fieldType <= 49)
/*      */         {
/*      */           
/*  563 */           intArray[repeatedFieldIndex++] = fieldOffset;
/*      */         }
/*      */       } 
/*      */       
/*  567 */       buffer[bufferIndex++] = fieldNumber;
/*  568 */       buffer[bufferIndex++] = (
/*  569 */         ((fieldTypeWithExtraBits & 0x200) != 0) ? true : false) | (
/*  570 */         ((fieldTypeWithExtraBits & 0x100) != 0) ? true : false) | (
/*  571 */         ((fieldTypeWithExtraBits & 0x800) != 0) ? Integer
/*  572 */         .MIN_VALUE : 
/*  573 */         0) | fieldType << 20 | fieldOffset;
/*      */ 
/*      */       
/*  576 */       buffer[bufferIndex++] = presenceMaskShift << 20 | presenceFieldOffset;
/*      */     } 
/*      */     
/*  579 */     return new MessageSchema<>(buffer, objects, minFieldNumber, maxFieldNumber, messageInfo
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  584 */         .getDefaultInstance(), false, intArray, checkInitialized, checkInitialized + mapFieldCount, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Field reflectField(Class<?> messageClass, String fieldName) {
/*      */     try {
/*  598 */       return messageClass.getDeclaredField(fieldName);
/*  599 */     } catch (NoSuchFieldException e) {
/*      */ 
/*      */       
/*  602 */       Field[] fields = messageClass.getDeclaredFields();
/*  603 */       for (Field field : fields) {
/*  604 */         if (fieldName.equals(field.getName())) {
/*  605 */           return field;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  611 */       throw new RuntimeException("Field " + fieldName + " for " + messageClass
/*      */ 
/*      */ 
/*      */           
/*  615 */           .getName() + " not found. Known fields are " + 
/*      */           
/*  617 */           Arrays.toString(fields), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <T> MessageSchema<T> newSchemaForMessageInfo(StructuralMessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
/*      */     int minFieldNumber, maxFieldNumber, combined[];
/*  629 */     FieldInfo[] fis = messageInfo.getFields();
/*      */ 
/*      */     
/*  632 */     if (fis.length == 0) {
/*  633 */       minFieldNumber = 0;
/*  634 */       maxFieldNumber = 0;
/*      */     } else {
/*  636 */       minFieldNumber = fis[0].getFieldNumber();
/*  637 */       maxFieldNumber = fis[fis.length - 1].getFieldNumber();
/*      */     } 
/*      */     
/*  640 */     int numEntries = fis.length;
/*      */     
/*  642 */     int[] buffer = new int[numEntries * 3];
/*  643 */     Object[] objects = new Object[numEntries * 2];
/*      */     
/*  645 */     int mapFieldCount = 0;
/*  646 */     int repeatedFieldCount = 0;
/*  647 */     for (FieldInfo fi : fis) {
/*  648 */       if (fi.getType() == FieldType.MAP) {
/*  649 */         mapFieldCount++;
/*  650 */       } else if (fi.getType().id() >= 18 && fi.getType().id() <= 49) {
/*      */ 
/*      */         
/*  653 */         repeatedFieldCount++;
/*      */       } 
/*      */     } 
/*  656 */     int[] mapFieldPositions = (mapFieldCount > 0) ? new int[mapFieldCount] : null;
/*  657 */     int[] repeatedFieldOffsets = (repeatedFieldCount > 0) ? new int[repeatedFieldCount] : null;
/*  658 */     mapFieldCount = 0;
/*  659 */     repeatedFieldCount = 0;
/*      */     
/*  661 */     int[] checkInitialized = messageInfo.getCheckInitialized();
/*  662 */     if (checkInitialized == null) {
/*  663 */       checkInitialized = EMPTY_INT_ARRAY;
/*      */     }
/*  665 */     int checkInitializedIndex = 0;
/*      */     
/*  667 */     int fieldIndex = 0;
/*  668 */     for (int bufferIndex = 0; fieldIndex < fis.length; bufferIndex += 3) {
/*  669 */       FieldInfo fi = fis[fieldIndex];
/*  670 */       int fieldNumber = fi.getFieldNumber();
/*      */ 
/*      */ 
/*      */       
/*  674 */       storeFieldData(fi, buffer, bufferIndex, objects);
/*      */ 
/*      */       
/*  677 */       if (checkInitializedIndex < checkInitialized.length && checkInitialized[checkInitializedIndex] == fieldNumber)
/*      */       {
/*  679 */         checkInitialized[checkInitializedIndex++] = bufferIndex;
/*      */       }
/*      */       
/*  682 */       if (fi.getType() == FieldType.MAP) {
/*  683 */         mapFieldPositions[mapFieldCount++] = bufferIndex;
/*  684 */       } else if (fi.getType().id() >= 18 && fi.getType().id() <= 49) {
/*      */ 
/*      */         
/*  687 */         repeatedFieldOffsets[repeatedFieldCount++] = 
/*  688 */           (int)UnsafeUtil.objectFieldOffset(fi.getField());
/*      */       } 
/*      */       
/*  691 */       fieldIndex++;
/*      */     } 
/*      */     
/*  694 */     if (mapFieldPositions == null) {
/*  695 */       mapFieldPositions = EMPTY_INT_ARRAY;
/*      */     }
/*  697 */     if (repeatedFieldOffsets == null) {
/*  698 */       repeatedFieldOffsets = EMPTY_INT_ARRAY;
/*      */     }
/*  700 */     int combinedLength = checkInitialized.length + mapFieldPositions.length + repeatedFieldOffsets.length;
/*      */ 
/*      */     
/*  703 */     if (combinedLength > 0) {
/*  704 */       combined = new int[combinedLength];
/*  705 */       System.arraycopy(checkInitialized, 0, combined, 0, checkInitialized.length);
/*  706 */       System.arraycopy(mapFieldPositions, 0, combined, checkInitialized.length, mapFieldPositions.length);
/*      */       
/*  708 */       System.arraycopy(repeatedFieldOffsets, 0, combined, checkInitialized.length + mapFieldPositions.length, repeatedFieldOffsets.length);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  715 */       combined = EMPTY_INT_ARRAY;
/*      */     } 
/*      */     
/*  718 */     return new MessageSchema<>(buffer, objects, minFieldNumber, maxFieldNumber, messageInfo
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  723 */         .getDefaultInstance(), true, combined, checkInitialized.length, checkInitialized.length + mapFieldPositions.length, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void storeFieldData(FieldInfo fi, int[] buffer, int bufferIndex, Object[] objects) {
/*      */     int fieldOffset, typeId, presenceMaskShift, presenceFieldOffset;
/*  742 */     OneofInfo oneof = fi.getOneof();
/*  743 */     if (oneof != null) {
/*  744 */       typeId = fi.getType().id() + 51;
/*  745 */       fieldOffset = (int)UnsafeUtil.objectFieldOffset(oneof.getValueField());
/*  746 */       presenceFieldOffset = (int)UnsafeUtil.objectFieldOffset(oneof.getCaseField());
/*  747 */       presenceMaskShift = 0;
/*      */     } else {
/*  749 */       FieldType type = fi.getType();
/*  750 */       fieldOffset = (int)UnsafeUtil.objectFieldOffset(fi.getField());
/*  751 */       typeId = type.id();
/*  752 */       if (!type.isList() && !type.isMap()) {
/*  753 */         Field presenceField = fi.getPresenceField();
/*  754 */         if (presenceField == null) {
/*  755 */           presenceFieldOffset = 1048575;
/*      */         } else {
/*  757 */           presenceFieldOffset = (int)UnsafeUtil.objectFieldOffset(presenceField);
/*      */         } 
/*  759 */         presenceMaskShift = Integer.numberOfTrailingZeros(fi.getPresenceMask());
/*      */       }
/*  761 */       else if (fi.getCachedSizeField() == null) {
/*  762 */         presenceFieldOffset = 0;
/*  763 */         presenceMaskShift = 0;
/*      */       } else {
/*  765 */         presenceFieldOffset = (int)UnsafeUtil.objectFieldOffset(fi.getCachedSizeField());
/*  766 */         presenceMaskShift = 0;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  771 */     buffer[bufferIndex] = fi.getFieldNumber();
/*  772 */     buffer[bufferIndex + 1] = (
/*  773 */       fi.isEnforceUtf8() ? true : false) | (
/*  774 */       fi.isRequired() ? true : false) | typeId << 20 | fieldOffset;
/*      */ 
/*      */     
/*  777 */     buffer[bufferIndex + 2] = presenceMaskShift << 20 | presenceFieldOffset;
/*      */     
/*  779 */     Object<?> messageFieldClass = (Object<?>)fi.getMessageFieldClass();
/*  780 */     if (fi.getMapDefaultEntry() != null) {
/*  781 */       objects[bufferIndex / 3 * 2] = fi.getMapDefaultEntry();
/*  782 */       if (messageFieldClass != null) {
/*  783 */         objects[bufferIndex / 3 * 2 + 1] = messageFieldClass;
/*  784 */       } else if (fi.getEnumVerifier() != null) {
/*  785 */         objects[bufferIndex / 3 * 2 + 1] = fi.getEnumVerifier();
/*      */       }
/*      */     
/*  788 */     } else if (messageFieldClass != null) {
/*  789 */       objects[bufferIndex / 3 * 2 + 1] = messageFieldClass;
/*  790 */     } else if (fi.getEnumVerifier() != null) {
/*  791 */       objects[bufferIndex / 3 * 2 + 1] = fi.getEnumVerifier();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T newInstance() {
/*  799 */     return (T)this.newInstanceSchema.newInstance(this.defaultInstance);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(T message, T other) {
/*  804 */     int bufferLength = this.buffer.length;
/*  805 */     for (int pos = 0; pos < bufferLength; pos += 3) {
/*  806 */       if (!equals(message, other, pos)) {
/*  807 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  811 */     Object messageUnknown = this.unknownFieldSchema.getFromMessage(message);
/*  812 */     Object otherUnknown = this.unknownFieldSchema.getFromMessage(other);
/*  813 */     if (!messageUnknown.equals(otherUnknown)) {
/*  814 */       return false;
/*      */     }
/*      */     
/*  817 */     if (this.hasExtensions) {
/*  818 */       FieldSet<?> messageExtensions = this.extensionSchema.getExtensions(message);
/*  819 */       FieldSet<?> otherExtensions = this.extensionSchema.getExtensions(other);
/*  820 */       return messageExtensions.equals(otherExtensions);
/*      */     } 
/*  822 */     return true;
/*      */   }
/*      */   
/*      */   private boolean equals(T message, T other, int pos) {
/*  826 */     int typeAndOffset = typeAndOffsetAt(pos);
/*  827 */     long offset = offset(typeAndOffset);
/*      */     
/*  829 */     switch (type(typeAndOffset)) {
/*      */       case 0:
/*  831 */         return (arePresentForEquals(message, other, pos) && 
/*  832 */           Double.doubleToLongBits(UnsafeUtil.getDouble(message, offset)) == 
/*  833 */           Double.doubleToLongBits(UnsafeUtil.getDouble(other, offset)));
/*      */       case 1:
/*  835 */         return (arePresentForEquals(message, other, pos) && 
/*  836 */           Float.floatToIntBits(UnsafeUtil.getFloat(message, offset)) == 
/*  837 */           Float.floatToIntBits(UnsafeUtil.getFloat(other, offset)));
/*      */       case 2:
/*  839 */         return (arePresentForEquals(message, other, pos) && 
/*  840 */           UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset));
/*      */       case 3:
/*  842 */         return (arePresentForEquals(message, other, pos) && 
/*  843 */           UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset));
/*      */       case 4:
/*  845 */         return (arePresentForEquals(message, other, pos) && 
/*  846 */           UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset));
/*      */       case 5:
/*  848 */         return (arePresentForEquals(message, other, pos) && 
/*  849 */           UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset));
/*      */       case 6:
/*  851 */         return (arePresentForEquals(message, other, pos) && 
/*  852 */           UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset));
/*      */       case 7:
/*  854 */         return (arePresentForEquals(message, other, pos) && 
/*  855 */           UnsafeUtil.getBoolean(message, offset) == UnsafeUtil.getBoolean(other, offset));
/*      */       case 8:
/*  857 */         return (arePresentForEquals(message, other, pos) && 
/*  858 */           SchemaUtil.safeEquals(
/*  859 */             UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset)));
/*      */       case 9:
/*  861 */         return (arePresentForEquals(message, other, pos) && 
/*  862 */           SchemaUtil.safeEquals(
/*  863 */             UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset)));
/*      */       case 10:
/*  865 */         return (arePresentForEquals(message, other, pos) && 
/*  866 */           SchemaUtil.safeEquals(
/*  867 */             UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset)));
/*      */       case 11:
/*  869 */         return (arePresentForEquals(message, other, pos) && 
/*  870 */           UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset));
/*      */       case 12:
/*  872 */         return (arePresentForEquals(message, other, pos) && 
/*  873 */           UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset));
/*      */       case 13:
/*  875 */         return (arePresentForEquals(message, other, pos) && 
/*  876 */           UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset));
/*      */       case 14:
/*  878 */         return (arePresentForEquals(message, other, pos) && 
/*  879 */           UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset));
/*      */       case 15:
/*  881 */         return (arePresentForEquals(message, other, pos) && 
/*  882 */           UnsafeUtil.getInt(message, offset) == UnsafeUtil.getInt(other, offset));
/*      */       case 16:
/*  884 */         return (arePresentForEquals(message, other, pos) && 
/*  885 */           UnsafeUtil.getLong(message, offset) == UnsafeUtil.getLong(other, offset));
/*      */       case 17:
/*  887 */         return (arePresentForEquals(message, other, pos) && 
/*  888 */           SchemaUtil.safeEquals(
/*  889 */             UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset)));
/*      */       
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 40:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*  923 */         return SchemaUtil.safeEquals(
/*  924 */             UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
/*      */       case 50:
/*  926 */         return SchemaUtil.safeEquals(
/*  927 */             UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset));
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 63:
/*      */       case 64:
/*      */       case 65:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*  946 */         return (isOneofCaseEqual(message, other, pos) && 
/*  947 */           SchemaUtil.safeEquals(
/*  948 */             UnsafeUtil.getObject(message, offset), UnsafeUtil.getObject(other, offset)));
/*      */     } 
/*      */     
/*  951 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode(T message) {
/*  957 */     int hashCode = 0;
/*  958 */     int bufferLength = this.buffer.length;
/*  959 */     for (int pos = 0; pos < bufferLength; pos += 3) {
/*  960 */       int protoHash; Object submessage; int typeAndOffset = typeAndOffsetAt(pos);
/*  961 */       int entryNumber = numberAt(pos);
/*      */       
/*  963 */       long offset = offset(typeAndOffset);
/*      */       
/*  965 */       switch (type(typeAndOffset)) {
/*      */ 
/*      */         
/*      */         case 0:
/*  969 */           hashCode = hashCode * 53 + Internal.hashLong(
/*  970 */               Double.doubleToLongBits(UnsafeUtil.getDouble(message, offset)));
/*      */           break;
/*      */         case 1:
/*  973 */           hashCode = hashCode * 53 + Float.floatToIntBits(UnsafeUtil.getFloat(message, offset));
/*      */           break;
/*      */         case 2:
/*  976 */           hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
/*      */           break;
/*      */         case 3:
/*  979 */           hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
/*      */           break;
/*      */         case 4:
/*  982 */           hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
/*      */           break;
/*      */         case 5:
/*  985 */           hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
/*      */           break;
/*      */         case 6:
/*  988 */           hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
/*      */           break;
/*      */         case 7:
/*  991 */           hashCode = hashCode * 53 + Internal.hashBoolean(UnsafeUtil.getBoolean(message, offset));
/*      */           break;
/*      */         case 8:
/*  994 */           hashCode = hashCode * 53 + ((String)UnsafeUtil.getObject(message, offset)).hashCode();
/*      */           break;
/*      */         
/*      */         case 9:
/*  998 */           protoHash = 37;
/*  999 */           submessage = UnsafeUtil.getObject(message, offset);
/* 1000 */           if (submessage != null) {
/* 1001 */             protoHash = submessage.hashCode();
/*      */           }
/* 1003 */           hashCode = 53 * hashCode + protoHash;
/*      */           break;
/*      */         
/*      */         case 10:
/* 1007 */           hashCode = hashCode * 53 + UnsafeUtil.getObject(message, offset).hashCode();
/*      */           break;
/*      */         case 11:
/* 1010 */           hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
/*      */           break;
/*      */         case 12:
/* 1013 */           hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
/*      */           break;
/*      */         case 13:
/* 1016 */           hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
/*      */           break;
/*      */         case 14:
/* 1019 */           hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
/*      */           break;
/*      */         case 15:
/* 1022 */           hashCode = hashCode * 53 + UnsafeUtil.getInt(message, offset);
/*      */           break;
/*      */         case 16:
/* 1025 */           hashCode = hashCode * 53 + Internal.hashLong(UnsafeUtil.getLong(message, offset));
/*      */           break;
/*      */ 
/*      */         
/*      */         case 17:
/* 1030 */           protoHash = 37;
/* 1031 */           submessage = UnsafeUtil.getObject(message, offset);
/* 1032 */           if (submessage != null) {
/* 1033 */             protoHash = submessage.hashCode();
/*      */           }
/* 1035 */           hashCode = 53 * hashCode + protoHash;
/*      */           break;
/*      */         
/*      */         case 18:
/*      */         case 19:
/*      */         case 20:
/*      */         case 21:
/*      */         case 22:
/*      */         case 23:
/*      */         case 24:
/*      */         case 25:
/*      */         case 26:
/*      */         case 27:
/*      */         case 28:
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/* 1070 */           hashCode = hashCode * 53 + UnsafeUtil.getObject(message, offset).hashCode();
/*      */           break;
/*      */         case 50:
/* 1073 */           hashCode = hashCode * 53 + UnsafeUtil.getObject(message, offset).hashCode();
/*      */           break;
/*      */         case 51:
/* 1076 */           if (isOneofPresent(message, entryNumber, pos))
/*      */           {
/*      */             
/* 1079 */             hashCode = hashCode * 53 + Internal.hashLong(Double.doubleToLongBits(oneofDoubleAt(message, offset)));
/*      */           }
/*      */           break;
/*      */         case 52:
/* 1083 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1084 */             hashCode = hashCode * 53 + Float.floatToIntBits(oneofFloatAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 53:
/* 1088 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1089 */             hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 54:
/* 1093 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1094 */             hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 55:
/* 1098 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1099 */             hashCode = hashCode * 53 + oneofIntAt(message, offset);
/*      */           }
/*      */           break;
/*      */         case 56:
/* 1103 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1104 */             hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 57:
/* 1108 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1109 */             hashCode = hashCode * 53 + oneofIntAt(message, offset);
/*      */           }
/*      */           break;
/*      */         case 58:
/* 1113 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1114 */             hashCode = hashCode * 53 + Internal.hashBoolean(oneofBooleanAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 59:
/* 1118 */           if (isOneofPresent(message, entryNumber, pos))
/*      */           {
/* 1120 */             hashCode = hashCode * 53 + ((String)UnsafeUtil.getObject(message, offset)).hashCode();
/*      */           }
/*      */           break;
/*      */         case 60:
/* 1124 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1125 */             Object object = UnsafeUtil.getObject(message, offset);
/* 1126 */             hashCode = 53 * hashCode + object.hashCode();
/*      */           } 
/*      */           break;
/*      */         case 61:
/* 1130 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1131 */             hashCode = hashCode * 53 + UnsafeUtil.getObject(message, offset).hashCode();
/*      */           }
/*      */           break;
/*      */         case 62:
/* 1135 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1136 */             hashCode = hashCode * 53 + oneofIntAt(message, offset);
/*      */           }
/*      */           break;
/*      */         case 63:
/* 1140 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1141 */             hashCode = hashCode * 53 + oneofIntAt(message, offset);
/*      */           }
/*      */           break;
/*      */         case 64:
/* 1145 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1146 */             hashCode = hashCode * 53 + oneofIntAt(message, offset);
/*      */           }
/*      */           break;
/*      */         case 65:
/* 1150 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1151 */             hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 66:
/* 1155 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1156 */             hashCode = hashCode * 53 + oneofIntAt(message, offset);
/*      */           }
/*      */           break;
/*      */         case 67:
/* 1160 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1161 */             hashCode = hashCode * 53 + Internal.hashLong(oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 68:
/* 1165 */           if (isOneofPresent(message, entryNumber, pos)) {
/* 1166 */             Object object = UnsafeUtil.getObject(message, offset);
/* 1167 */             hashCode = 53 * hashCode + object.hashCode();
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 1176 */     hashCode = hashCode * 53 + this.unknownFieldSchema.getFromMessage(message).hashCode();
/*      */     
/* 1178 */     if (this.hasExtensions) {
/* 1179 */       hashCode = hashCode * 53 + this.extensionSchema.getExtensions(message).hashCode();
/*      */     }
/*      */     
/* 1182 */     return hashCode;
/*      */   }
/*      */ 
/*      */   
/*      */   public void mergeFrom(T message, T other) {
/* 1187 */     checkMutable(message);
/* 1188 */     if (other == null) {
/* 1189 */       throw new NullPointerException();
/*      */     }
/* 1191 */     for (int i = 0; i < this.buffer.length; i += 3)
/*      */     {
/* 1193 */       mergeSingleField(message, other, i);
/*      */     }
/*      */     
/* 1196 */     SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, message, other);
/*      */     
/* 1198 */     if (this.hasExtensions) {
/* 1199 */       SchemaUtil.mergeExtensions(this.extensionSchema, message, other);
/*      */     }
/*      */   }
/*      */   
/*      */   private void mergeSingleField(T message, T other, int pos) {
/* 1204 */     int typeAndOffset = typeAndOffsetAt(pos);
/* 1205 */     long offset = offset(typeAndOffset);
/* 1206 */     int number = numberAt(pos);
/*      */     
/* 1208 */     switch (type(typeAndOffset)) {
/*      */       case 0:
/* 1210 */         if (isFieldPresent(other, pos)) {
/* 1211 */           UnsafeUtil.putDouble(message, offset, UnsafeUtil.getDouble(other, offset));
/* 1212 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 1:
/* 1216 */         if (isFieldPresent(other, pos)) {
/* 1217 */           UnsafeUtil.putFloat(message, offset, UnsafeUtil.getFloat(other, offset));
/* 1218 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 2:
/* 1222 */         if (isFieldPresent(other, pos)) {
/* 1223 */           UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
/* 1224 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 3:
/* 1228 */         if (isFieldPresent(other, pos)) {
/* 1229 */           UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
/* 1230 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 4:
/* 1234 */         if (isFieldPresent(other, pos)) {
/* 1235 */           UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
/* 1236 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 5:
/* 1240 */         if (isFieldPresent(other, pos)) {
/* 1241 */           UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
/* 1242 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 6:
/* 1246 */         if (isFieldPresent(other, pos)) {
/* 1247 */           UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
/* 1248 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 7:
/* 1252 */         if (isFieldPresent(other, pos)) {
/* 1253 */           UnsafeUtil.putBoolean(message, offset, UnsafeUtil.getBoolean(other, offset));
/* 1254 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 8:
/* 1258 */         if (isFieldPresent(other, pos)) {
/* 1259 */           UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
/* 1260 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 9:
/* 1264 */         mergeMessage(message, other, pos);
/*      */         break;
/*      */       case 10:
/* 1267 */         if (isFieldPresent(other, pos)) {
/* 1268 */           UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
/* 1269 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 11:
/* 1273 */         if (isFieldPresent(other, pos)) {
/* 1274 */           UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
/* 1275 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 12:
/* 1279 */         if (isFieldPresent(other, pos)) {
/* 1280 */           UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
/* 1281 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 13:
/* 1285 */         if (isFieldPresent(other, pos)) {
/* 1286 */           UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
/* 1287 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 14:
/* 1291 */         if (isFieldPresent(other, pos)) {
/* 1292 */           UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
/* 1293 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 15:
/* 1297 */         if (isFieldPresent(other, pos)) {
/* 1298 */           UnsafeUtil.putInt(message, offset, UnsafeUtil.getInt(other, offset));
/* 1299 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 16:
/* 1303 */         if (isFieldPresent(other, pos)) {
/* 1304 */           UnsafeUtil.putLong(message, offset, UnsafeUtil.getLong(other, offset));
/* 1305 */           setFieldPresent(message, pos);
/*      */         } 
/*      */         break;
/*      */       case 17:
/* 1309 */         mergeMessage(message, other, pos);
/*      */         break;
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 40:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/* 1343 */         this.listFieldSchema.mergeListsAt(message, other, offset);
/*      */         break;
/*      */       case 50:
/* 1346 */         SchemaUtil.mergeMap(this.mapFieldSchema, message, other, offset);
/*      */         break;
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/* 1357 */         if (isOneofPresent(other, number, pos)) {
/* 1358 */           UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
/* 1359 */           setOneofPresent(message, number, pos);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 60:
/* 1364 */         mergeOneofMessage(message, other, pos);
/*      */         break;
/*      */       case 61:
/*      */       case 62:
/*      */       case 63:
/*      */       case 64:
/*      */       case 65:
/*      */       case 66:
/*      */       case 67:
/* 1373 */         if (isOneofPresent(other, number, pos)) {
/* 1374 */           UnsafeUtil.putObject(message, offset, UnsafeUtil.getObject(other, offset));
/* 1375 */           setOneofPresent(message, number, pos);
/*      */         } 
/*      */         break;
/*      */       case 68:
/* 1379 */         mergeOneofMessage(message, other, pos);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeMessage(T targetParent, T sourceParent, int pos) {
/* 1387 */     if (!isFieldPresent(sourceParent, pos)) {
/*      */       return;
/*      */     }
/*      */     
/* 1391 */     int typeAndOffset = typeAndOffsetAt(pos);
/* 1392 */     long offset = offset(typeAndOffset);
/*      */     
/* 1394 */     Object source = UNSAFE.getObject(sourceParent, offset);
/* 1395 */     if (source == null) {
/* 1396 */       throw new IllegalStateException("Source subfield " + 
/* 1397 */           numberAt(pos) + " is present but null: " + sourceParent);
/*      */     }
/*      */     
/* 1400 */     Schema<Object> fieldSchema = getMessageFieldSchema(pos);
/* 1401 */     if (!isFieldPresent(targetParent, pos)) {
/* 1402 */       if (!isMutable(source)) {
/*      */         
/* 1404 */         UNSAFE.putObject(targetParent, offset, source);
/*      */       } else {
/*      */         
/* 1407 */         Object copyOfSource = fieldSchema.newInstance();
/* 1408 */         fieldSchema.mergeFrom(copyOfSource, source);
/* 1409 */         UNSAFE.putObject(targetParent, offset, copyOfSource);
/*      */       } 
/* 1411 */       setFieldPresent(targetParent, pos);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1416 */     Object target = UNSAFE.getObject(targetParent, offset);
/* 1417 */     if (!isMutable(target)) {
/* 1418 */       Object newInstance = fieldSchema.newInstance();
/* 1419 */       fieldSchema.mergeFrom(newInstance, target);
/* 1420 */       UNSAFE.putObject(targetParent, offset, newInstance);
/* 1421 */       target = newInstance;
/*      */     } 
/* 1423 */     fieldSchema.mergeFrom(target, source);
/*      */   }
/*      */   
/*      */   private void mergeOneofMessage(T targetParent, T sourceParent, int pos) {
/* 1427 */     int number = numberAt(pos);
/* 1428 */     if (!isOneofPresent(sourceParent, number, pos)) {
/*      */       return;
/*      */     }
/*      */     
/* 1432 */     long offset = offset(typeAndOffsetAt(pos));
/* 1433 */     Object source = UNSAFE.getObject(sourceParent, offset);
/* 1434 */     if (source == null) {
/* 1435 */       throw new IllegalStateException("Source subfield " + 
/* 1436 */           numberAt(pos) + " is present but null: " + sourceParent);
/*      */     }
/*      */     
/* 1439 */     Schema<Object> fieldSchema = getMessageFieldSchema(pos);
/* 1440 */     if (!isOneofPresent(targetParent, number, pos)) {
/* 1441 */       if (!isMutable(source)) {
/*      */         
/* 1443 */         UNSAFE.putObject(targetParent, offset, source);
/*      */       } else {
/*      */         
/* 1446 */         Object copyOfSource = fieldSchema.newInstance();
/* 1447 */         fieldSchema.mergeFrom(copyOfSource, source);
/* 1448 */         UNSAFE.putObject(targetParent, offset, copyOfSource);
/*      */       } 
/* 1450 */       setOneofPresent(targetParent, number, pos);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1455 */     Object target = UNSAFE.getObject(targetParent, offset);
/* 1456 */     if (!isMutable(target)) {
/* 1457 */       Object newInstance = fieldSchema.newInstance();
/* 1458 */       fieldSchema.mergeFrom(newInstance, target);
/* 1459 */       UNSAFE.putObject(targetParent, offset, newInstance);
/* 1460 */       target = newInstance;
/*      */     } 
/* 1462 */     fieldSchema.mergeFrom(target, source);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSerializedSize(T message) {
/* 1468 */     int size = 0;
/*      */     
/* 1470 */     Unsafe unsafe = UNSAFE;
/* 1471 */     int currentPresenceFieldOffset = 1048575;
/* 1472 */     int currentPresenceField = 0;
/* 1473 */     for (int i = 0; i < this.buffer.length; i += 3) {
/* 1474 */       int fieldSize, typeAndOffset = typeAndOffsetAt(i);
/* 1475 */       int fieldType = type(typeAndOffset);
/* 1476 */       int number = numberAt(i);
/*      */       
/* 1478 */       int presenceMask = 0;
/* 1479 */       int presenceMaskAndOffset = this.buffer[i + 2];
/* 1480 */       int presenceOrCachedSizeFieldOffset = presenceMaskAndOffset & 0xFFFFF;
/* 1481 */       if (fieldType <= 17) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1486 */         if (presenceOrCachedSizeFieldOffset != currentPresenceFieldOffset) {
/* 1487 */           currentPresenceFieldOffset = presenceOrCachedSizeFieldOffset;
/*      */ 
/*      */ 
/*      */           
/* 1491 */           currentPresenceField = (currentPresenceFieldOffset == 1048575) ? 0 : unsafe.getInt(message, currentPresenceFieldOffset);
/*      */         } 
/*      */         
/* 1494 */         presenceMask = 1 << presenceMaskAndOffset >>> 20;
/*      */       } 
/*      */       
/* 1497 */       long offset = offset(typeAndOffset);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1502 */       int cachedSizeOffset = (fieldType >= FieldType.DOUBLE_LIST_PACKED.id() && fieldType <= FieldType.SINT64_LIST_PACKED.id()) ? presenceOrCachedSizeFieldOffset : 0;
/*      */       
/* 1504 */       switch (fieldType) {
/*      */         case 0:
/* 1506 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1508 */             size += CodedOutputStream.computeDoubleSize(number, 0.0D);
/*      */           }
/*      */           break;
/*      */         case 1:
/* 1512 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1514 */             size += CodedOutputStream.computeFloatSize(number, 0.0F);
/*      */           }
/*      */           break;
/*      */         case 2:
/* 1518 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1520 */             size += CodedOutputStream.computeInt64Size(number, unsafe.getLong(message, offset));
/*      */           }
/*      */           break;
/*      */         case 3:
/* 1524 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1526 */             size += CodedOutputStream.computeUInt64Size(number, unsafe.getLong(message, offset));
/*      */           }
/*      */           break;
/*      */         case 4:
/* 1530 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1532 */             size += CodedOutputStream.computeInt32Size(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 5:
/* 1536 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1538 */             size += CodedOutputStream.computeFixed64Size(number, 0L);
/*      */           }
/*      */           break;
/*      */         case 6:
/* 1542 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1544 */             size += CodedOutputStream.computeFixed32Size(number, 0);
/*      */           }
/*      */           break;
/*      */         case 7:
/* 1548 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1550 */             size += CodedOutputStream.computeBoolSize(number, true);
/*      */           }
/*      */           break;
/*      */         case 8:
/* 1554 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask)) {
/*      */             
/* 1556 */             Object value = unsafe.getObject(message, offset);
/* 1557 */             if (value instanceof ByteString) {
/* 1558 */               size += CodedOutputStream.computeBytesSize(number, (ByteString)value); break;
/*      */             } 
/* 1560 */             size += CodedOutputStream.computeStringSize(number, (String)value);
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 9:
/* 1565 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask)) {
/*      */             
/* 1567 */             Object value = unsafe.getObject(message, offset);
/* 1568 */             size += SchemaUtil.computeSizeMessage(number, value, getMessageFieldSchema(i));
/*      */           } 
/*      */           break;
/*      */         case 10:
/* 1572 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask)) {
/*      */             
/* 1574 */             ByteString value = (ByteString)unsafe.getObject(message, offset);
/* 1575 */             size += CodedOutputStream.computeBytesSize(number, value);
/*      */           } 
/*      */           break;
/*      */         case 11:
/* 1579 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1581 */             size += CodedOutputStream.computeUInt32Size(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 12:
/* 1585 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1587 */             size += CodedOutputStream.computeEnumSize(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 13:
/* 1591 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1593 */             size += CodedOutputStream.computeSFixed32Size(number, 0);
/*      */           }
/*      */           break;
/*      */         case 14:
/* 1597 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1599 */             size += CodedOutputStream.computeSFixed64Size(number, 0L);
/*      */           }
/*      */           break;
/*      */         case 15:
/* 1603 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1605 */             size += CodedOutputStream.computeSInt32Size(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 16:
/* 1609 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1611 */             size += CodedOutputStream.computeSInt64Size(number, unsafe.getLong(message, offset));
/*      */           }
/*      */           break;
/*      */         case 17:
/* 1615 */           if (isFieldPresent(message, i, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 1617 */             size += 
/* 1618 */               SchemaUtil.computeGroupSize(number, (MessageLite)unsafe
/*      */                 
/* 1620 */                 .getObject(message, offset), 
/* 1621 */                 getMessageFieldSchema(i));
/*      */           }
/*      */           break;
/*      */         case 18:
/* 1625 */           size += 
/* 1626 */             SchemaUtil.computeSizeFixed64List(number, (List)unsafe
/* 1627 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 19:
/* 1630 */           size += 
/* 1631 */             SchemaUtil.computeSizeFixed32List(number, (List)unsafe
/* 1632 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 20:
/* 1635 */           size += 
/* 1636 */             SchemaUtil.computeSizeInt64List(number, (List<Long>)unsafe
/* 1637 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 21:
/* 1640 */           size += 
/* 1641 */             SchemaUtil.computeSizeUInt64List(number, (List<Long>)unsafe
/* 1642 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 22:
/* 1645 */           size += 
/* 1646 */             SchemaUtil.computeSizeInt32List(number, (List<Integer>)unsafe
/* 1647 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 23:
/* 1650 */           size += 
/* 1651 */             SchemaUtil.computeSizeFixed64List(number, (List)unsafe
/* 1652 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 24:
/* 1655 */           size += 
/* 1656 */             SchemaUtil.computeSizeFixed32List(number, (List)unsafe
/* 1657 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 25:
/* 1660 */           size += 
/* 1661 */             SchemaUtil.computeSizeBoolList(number, (List)unsafe
/* 1662 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 26:
/* 1665 */           size += 
/* 1666 */             SchemaUtil.computeSizeStringList(number, (List)unsafe.getObject(message, offset));
/*      */           break;
/*      */         case 27:
/* 1669 */           size += 
/* 1670 */             SchemaUtil.computeSizeMessageList(number, (List)unsafe
/* 1671 */               .getObject(message, offset), getMessageFieldSchema(i));
/*      */           break;
/*      */         case 28:
/* 1674 */           size += 
/* 1675 */             SchemaUtil.computeSizeByteStringList(number, (List<ByteString>)unsafe
/* 1676 */               .getObject(message, offset));
/*      */           break;
/*      */         case 29:
/* 1679 */           size += 
/* 1680 */             SchemaUtil.computeSizeUInt32List(number, (List<Integer>)unsafe
/* 1681 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 30:
/* 1684 */           size += 
/* 1685 */             SchemaUtil.computeSizeEnumList(number, (List<Integer>)unsafe
/* 1686 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 31:
/* 1689 */           size += 
/* 1690 */             SchemaUtil.computeSizeFixed32List(number, (List)unsafe
/* 1691 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 32:
/* 1694 */           size += 
/* 1695 */             SchemaUtil.computeSizeFixed64List(number, (List)unsafe
/* 1696 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 33:
/* 1699 */           size += 
/* 1700 */             SchemaUtil.computeSizeSInt32List(number, (List<Integer>)unsafe
/* 1701 */               .getObject(message, offset), false);
/*      */           break;
/*      */         case 34:
/* 1704 */           size += 
/* 1705 */             SchemaUtil.computeSizeSInt64List(number, (List<Long>)unsafe
/* 1706 */               .getObject(message, offset), false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 35:
/* 1711 */           fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List)unsafe
/* 1712 */               .getObject(message, offset));
/* 1713 */           if (fieldSize > 0) {
/* 1714 */             if (this.useCachedSizeField) {
/* 1715 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1717 */             size += 
/* 1718 */               CodedOutputStream.computeTagSize(number) + 
/* 1719 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 36:
/* 1727 */           fieldSize = SchemaUtil.computeSizeFixed32ListNoTag((List)unsafe
/* 1728 */               .getObject(message, offset));
/* 1729 */           if (fieldSize > 0) {
/* 1730 */             if (this.useCachedSizeField) {
/* 1731 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1733 */             size += 
/* 1734 */               CodedOutputStream.computeTagSize(number) + 
/* 1735 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 37:
/* 1743 */           fieldSize = SchemaUtil.computeSizeInt64ListNoTag((List<Long>)unsafe
/* 1744 */               .getObject(message, offset));
/* 1745 */           if (fieldSize > 0) {
/* 1746 */             if (this.useCachedSizeField) {
/* 1747 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1749 */             size += 
/* 1750 */               CodedOutputStream.computeTagSize(number) + 
/* 1751 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 38:
/* 1759 */           fieldSize = SchemaUtil.computeSizeUInt64ListNoTag((List<Long>)unsafe
/* 1760 */               .getObject(message, offset));
/* 1761 */           if (fieldSize > 0) {
/* 1762 */             if (this.useCachedSizeField) {
/* 1763 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1765 */             size += 
/* 1766 */               CodedOutputStream.computeTagSize(number) + 
/* 1767 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 39:
/* 1775 */           fieldSize = SchemaUtil.computeSizeInt32ListNoTag((List<Integer>)unsafe
/* 1776 */               .getObject(message, offset));
/* 1777 */           if (fieldSize > 0) {
/* 1778 */             if (this.useCachedSizeField) {
/* 1779 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1781 */             size += 
/* 1782 */               CodedOutputStream.computeTagSize(number) + 
/* 1783 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 40:
/* 1791 */           fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List)unsafe
/* 1792 */               .getObject(message, offset));
/* 1793 */           if (fieldSize > 0) {
/* 1794 */             if (this.useCachedSizeField) {
/* 1795 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1797 */             size += 
/* 1798 */               CodedOutputStream.computeTagSize(number) + 
/* 1799 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 41:
/* 1807 */           fieldSize = SchemaUtil.computeSizeFixed32ListNoTag((List)unsafe
/* 1808 */               .getObject(message, offset));
/* 1809 */           if (fieldSize > 0) {
/* 1810 */             if (this.useCachedSizeField) {
/* 1811 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1813 */             size += 
/* 1814 */               CodedOutputStream.computeTagSize(number) + 
/* 1815 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 42:
/* 1823 */           fieldSize = SchemaUtil.computeSizeBoolListNoTag((List)unsafe
/* 1824 */               .getObject(message, offset));
/* 1825 */           if (fieldSize > 0) {
/* 1826 */             if (this.useCachedSizeField) {
/* 1827 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1829 */             size += 
/* 1830 */               CodedOutputStream.computeTagSize(number) + 
/* 1831 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 43:
/* 1839 */           fieldSize = SchemaUtil.computeSizeUInt32ListNoTag((List<Integer>)unsafe
/* 1840 */               .getObject(message, offset));
/* 1841 */           if (fieldSize > 0) {
/* 1842 */             if (this.useCachedSizeField) {
/* 1843 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1845 */             size += 
/* 1846 */               CodedOutputStream.computeTagSize(number) + 
/* 1847 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 44:
/* 1855 */           fieldSize = SchemaUtil.computeSizeEnumListNoTag((List<Integer>)unsafe
/* 1856 */               .getObject(message, offset));
/* 1857 */           if (fieldSize > 0) {
/* 1858 */             if (this.useCachedSizeField) {
/* 1859 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1861 */             size += 
/* 1862 */               CodedOutputStream.computeTagSize(number) + 
/* 1863 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 45:
/* 1871 */           fieldSize = SchemaUtil.computeSizeFixed32ListNoTag((List)unsafe
/* 1872 */               .getObject(message, offset));
/* 1873 */           if (fieldSize > 0) {
/* 1874 */             if (this.useCachedSizeField) {
/* 1875 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1877 */             size += 
/* 1878 */               CodedOutputStream.computeTagSize(number) + 
/* 1879 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 46:
/* 1887 */           fieldSize = SchemaUtil.computeSizeFixed64ListNoTag((List)unsafe
/* 1888 */               .getObject(message, offset));
/* 1889 */           if (fieldSize > 0) {
/* 1890 */             if (this.useCachedSizeField) {
/* 1891 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1893 */             size += 
/* 1894 */               CodedOutputStream.computeTagSize(number) + 
/* 1895 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 47:
/* 1903 */           fieldSize = SchemaUtil.computeSizeSInt32ListNoTag((List<Integer>)unsafe
/* 1904 */               .getObject(message, offset));
/* 1905 */           if (fieldSize > 0) {
/* 1906 */             if (this.useCachedSizeField) {
/* 1907 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1909 */             size += 
/* 1910 */               CodedOutputStream.computeTagSize(number) + 
/* 1911 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         case 48:
/* 1919 */           fieldSize = SchemaUtil.computeSizeSInt64ListNoTag((List<Long>)unsafe
/* 1920 */               .getObject(message, offset));
/* 1921 */           if (fieldSize > 0) {
/* 1922 */             if (this.useCachedSizeField) {
/* 1923 */               unsafe.putInt(message, cachedSizeOffset, fieldSize);
/*      */             }
/* 1925 */             size += 
/* 1926 */               CodedOutputStream.computeTagSize(number) + 
/* 1927 */               CodedOutputStream.computeUInt32SizeNoTag(fieldSize) + fieldSize;
/*      */           } 
/*      */           break;
/*      */ 
/*      */         
/*      */         case 49:
/* 1933 */           size += 
/* 1934 */             SchemaUtil.computeSizeGroupList(number, (List<MessageLite>)unsafe
/*      */               
/* 1936 */               .getObject(message, offset), 
/* 1937 */               getMessageFieldSchema(i));
/*      */           break;
/*      */         
/*      */         case 50:
/* 1941 */           size += this.mapFieldSchema
/* 1942 */             .getSerializedSize(number, unsafe
/* 1943 */               .getObject(message, offset), getMapFieldDefaultEntry(i));
/*      */           break;
/*      */         case 51:
/* 1946 */           if (isOneofPresent(message, number, i)) {
/* 1947 */             size += CodedOutputStream.computeDoubleSize(number, 0.0D);
/*      */           }
/*      */           break;
/*      */         case 52:
/* 1951 */           if (isOneofPresent(message, number, i)) {
/* 1952 */             size += CodedOutputStream.computeFloatSize(number, 0.0F);
/*      */           }
/*      */           break;
/*      */         case 53:
/* 1956 */           if (isOneofPresent(message, number, i)) {
/* 1957 */             size += CodedOutputStream.computeInt64Size(number, oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 54:
/* 1961 */           if (isOneofPresent(message, number, i)) {
/* 1962 */             size += CodedOutputStream.computeUInt64Size(number, oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 55:
/* 1966 */           if (isOneofPresent(message, number, i)) {
/* 1967 */             size += CodedOutputStream.computeInt32Size(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 56:
/* 1971 */           if (isOneofPresent(message, number, i)) {
/* 1972 */             size += CodedOutputStream.computeFixed64Size(number, 0L);
/*      */           }
/*      */           break;
/*      */         case 57:
/* 1976 */           if (isOneofPresent(message, number, i)) {
/* 1977 */             size += CodedOutputStream.computeFixed32Size(number, 0);
/*      */           }
/*      */           break;
/*      */         case 58:
/* 1981 */           if (isOneofPresent(message, number, i)) {
/* 1982 */             size += CodedOutputStream.computeBoolSize(number, true);
/*      */           }
/*      */           break;
/*      */         case 59:
/* 1986 */           if (isOneofPresent(message, number, i)) {
/* 1987 */             Object value = unsafe.getObject(message, offset);
/* 1988 */             if (value instanceof ByteString) {
/* 1989 */               size += CodedOutputStream.computeBytesSize(number, (ByteString)value); break;
/*      */             } 
/* 1991 */             size += CodedOutputStream.computeStringSize(number, (String)value);
/*      */           } 
/*      */           break;
/*      */         
/*      */         case 60:
/* 1996 */           if (isOneofPresent(message, number, i)) {
/* 1997 */             Object value = unsafe.getObject(message, offset);
/* 1998 */             size += SchemaUtil.computeSizeMessage(number, value, getMessageFieldSchema(i));
/*      */           } 
/*      */           break;
/*      */         case 61:
/* 2002 */           if (isOneofPresent(message, number, i)) {
/* 2003 */             size += 
/* 2004 */               CodedOutputStream.computeBytesSize(number, (ByteString)unsafe
/* 2005 */                 .getObject(message, offset));
/*      */           }
/*      */           break;
/*      */         case 62:
/* 2009 */           if (isOneofPresent(message, number, i)) {
/* 2010 */             size += CodedOutputStream.computeUInt32Size(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 63:
/* 2014 */           if (isOneofPresent(message, number, i)) {
/* 2015 */             size += CodedOutputStream.computeEnumSize(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 64:
/* 2019 */           if (isOneofPresent(message, number, i)) {
/* 2020 */             size += CodedOutputStream.computeSFixed32Size(number, 0);
/*      */           }
/*      */           break;
/*      */         case 65:
/* 2024 */           if (isOneofPresent(message, number, i)) {
/* 2025 */             size += CodedOutputStream.computeSFixed64Size(number, 0L);
/*      */           }
/*      */           break;
/*      */         case 66:
/* 2029 */           if (isOneofPresent(message, number, i)) {
/* 2030 */             size += CodedOutputStream.computeSInt32Size(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 67:
/* 2034 */           if (isOneofPresent(message, number, i)) {
/* 2035 */             size += CodedOutputStream.computeSInt64Size(number, oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 68:
/* 2039 */           if (isOneofPresent(message, number, i)) {
/* 2040 */             size += 
/* 2041 */               SchemaUtil.computeGroupSize(number, (MessageLite)unsafe
/*      */                 
/* 2043 */                 .getObject(message, offset), 
/* 2044 */                 getMessageFieldSchema(i));
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 2052 */     size += getUnknownFieldsSerializedSize(this.unknownFieldSchema, message);
/*      */     
/* 2054 */     if (this.hasExtensions) {
/* 2055 */       size += this.extensionSchema.getExtensions(message).getSerializedSize();
/*      */     }
/*      */     
/* 2058 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> schema, T message) {
/* 2063 */     UT unknowns = schema.getFromMessage(message);
/* 2064 */     return schema.getSerializedSize(unknowns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(T message, Writer writer) throws IOException {
/* 2072 */     if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
/* 2073 */       writeFieldsInDescendingOrder(message, writer);
/*      */     } else {
/* 2075 */       writeFieldsInAscendingOrder(message, writer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFieldsInAscendingOrder(T message, Writer writer) throws IOException {
/* 2081 */     Iterator<? extends Map.Entry<?, ?>> extensionIterator = null;
/* 2082 */     Map.Entry<?, ?> nextExtension = null;
/* 2083 */     if (this.hasExtensions) {
/* 2084 */       FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
/* 2085 */       if (!extensions.isEmpty()) {
/* 2086 */         extensionIterator = extensions.iterator();
/* 2087 */         nextExtension = extensionIterator.next();
/*      */       } 
/*      */     } 
/*      */     
/* 2091 */     int currentPresenceFieldOffset = 1048575;
/* 2092 */     int currentPresenceField = 0;
/* 2093 */     int bufferLength = this.buffer.length;
/* 2094 */     Unsafe unsafe = UNSAFE;
/* 2095 */     for (int pos = 0; pos < bufferLength; pos += 3) {
/* 2096 */       int typeAndOffset = typeAndOffsetAt(pos);
/* 2097 */       int number = numberAt(pos);
/* 2098 */       int fieldType = type(typeAndOffset);
/*      */       
/* 2100 */       int presenceMask = 0;
/* 2101 */       if (fieldType <= 17) {
/* 2102 */         int presenceMaskAndOffset = this.buffer[pos + 2];
/* 2103 */         int presenceFieldOffset = presenceMaskAndOffset & 0xFFFFF;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2109 */         if (presenceFieldOffset != currentPresenceFieldOffset) {
/* 2110 */           currentPresenceFieldOffset = presenceFieldOffset;
/*      */ 
/*      */ 
/*      */           
/* 2114 */           currentPresenceField = (currentPresenceFieldOffset == 1048575) ? 0 : unsafe.getInt(message, presenceFieldOffset);
/*      */         } 
/*      */         
/* 2117 */         presenceMask = 1 << presenceMaskAndOffset >>> 20;
/*      */       } 
/*      */ 
/*      */       
/* 2121 */       while (nextExtension != null && this.extensionSchema.extensionNumber(nextExtension) <= number) {
/* 2122 */         this.extensionSchema.serializeExtension(writer, nextExtension);
/* 2123 */         nextExtension = extensionIterator.hasNext() ? extensionIterator.next() : null;
/*      */       } 
/* 2125 */       long offset = offset(typeAndOffset);
/*      */       
/* 2127 */       switch (fieldType) {
/*      */         case 0:
/* 2129 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2131 */             writer.writeDouble(number, doubleAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 1:
/* 2135 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2137 */             writer.writeFloat(number, floatAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 2:
/* 2141 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2143 */             writer.writeInt64(number, unsafe.getLong(message, offset));
/*      */           }
/*      */           break;
/*      */         case 3:
/* 2147 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2149 */             writer.writeUInt64(number, unsafe.getLong(message, offset));
/*      */           }
/*      */           break;
/*      */         case 4:
/* 2153 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2155 */             writer.writeInt32(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 5:
/* 2159 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2161 */             writer.writeFixed64(number, unsafe.getLong(message, offset));
/*      */           }
/*      */           break;
/*      */         case 6:
/* 2165 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2167 */             writer.writeFixed32(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 7:
/* 2171 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2173 */             writer.writeBool(number, booleanAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 8:
/* 2177 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2179 */             writeString(number, unsafe.getObject(message, offset), writer);
/*      */           }
/*      */           break;
/*      */         case 9:
/* 2183 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask)) {
/*      */             
/* 2185 */             Object value = unsafe.getObject(message, offset);
/* 2186 */             writer.writeMessage(number, value, getMessageFieldSchema(pos));
/*      */           } 
/*      */           break;
/*      */         case 10:
/* 2190 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2192 */             writer.writeBytes(number, (ByteString)unsafe.getObject(message, offset));
/*      */           }
/*      */           break;
/*      */         case 11:
/* 2196 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2198 */             writer.writeUInt32(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 12:
/* 2202 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2204 */             writer.writeEnum(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 13:
/* 2208 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2210 */             writer.writeSFixed32(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 14:
/* 2214 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2216 */             writer.writeSFixed64(number, unsafe.getLong(message, offset));
/*      */           }
/*      */           break;
/*      */         case 15:
/* 2220 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2222 */             writer.writeSInt32(number, unsafe.getInt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 16:
/* 2226 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2228 */             writer.writeSInt64(number, unsafe.getLong(message, offset));
/*      */           }
/*      */           break;
/*      */         case 17:
/* 2232 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */           {
/* 2234 */             writer.writeGroup(number, unsafe
/* 2235 */                 .getObject(message, offset), getMessageFieldSchema(pos));
/*      */           }
/*      */           break;
/*      */         case 18:
/* 2239 */           SchemaUtil.writeDoubleList(
/* 2240 */               numberAt(pos), (List<Double>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 19:
/* 2243 */           SchemaUtil.writeFloatList(
/* 2244 */               numberAt(pos), (List<Float>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 20:
/* 2247 */           SchemaUtil.writeInt64List(
/* 2248 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 21:
/* 2251 */           SchemaUtil.writeUInt64List(
/* 2252 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 22:
/* 2255 */           SchemaUtil.writeInt32List(
/* 2256 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 23:
/* 2259 */           SchemaUtil.writeFixed64List(
/* 2260 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 24:
/* 2263 */           SchemaUtil.writeFixed32List(
/* 2264 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 25:
/* 2267 */           SchemaUtil.writeBoolList(
/* 2268 */               numberAt(pos), (List<Boolean>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 26:
/* 2271 */           SchemaUtil.writeStringList(
/* 2272 */               numberAt(pos), (List<String>)unsafe.getObject(message, offset), writer);
/*      */           break;
/*      */         case 27:
/* 2275 */           SchemaUtil.writeMessageList(
/* 2276 */               numberAt(pos), (List)unsafe
/* 2277 */               .getObject(message, offset), writer, 
/*      */               
/* 2279 */               getMessageFieldSchema(pos));
/*      */           break;
/*      */         case 28:
/* 2282 */           SchemaUtil.writeBytesList(
/* 2283 */               numberAt(pos), (List<ByteString>)unsafe.getObject(message, offset), writer);
/*      */           break;
/*      */         case 29:
/* 2286 */           SchemaUtil.writeUInt32List(
/* 2287 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 30:
/* 2290 */           SchemaUtil.writeEnumList(
/* 2291 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 31:
/* 2294 */           SchemaUtil.writeSFixed32List(
/* 2295 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 32:
/* 2298 */           SchemaUtil.writeSFixed64List(
/* 2299 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 33:
/* 2302 */           SchemaUtil.writeSInt32List(
/* 2303 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         case 34:
/* 2306 */           SchemaUtil.writeSInt64List(
/* 2307 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, false);
/*      */           break;
/*      */         
/*      */         case 35:
/* 2311 */           SchemaUtil.writeDoubleList(
/* 2312 */               numberAt(pos), (List<Double>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 36:
/* 2315 */           SchemaUtil.writeFloatList(
/* 2316 */               numberAt(pos), (List<Float>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 37:
/* 2319 */           SchemaUtil.writeInt64List(
/* 2320 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 38:
/* 2323 */           SchemaUtil.writeUInt64List(
/* 2324 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 39:
/* 2327 */           SchemaUtil.writeInt32List(
/* 2328 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 40:
/* 2331 */           SchemaUtil.writeFixed64List(
/* 2332 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 41:
/* 2335 */           SchemaUtil.writeFixed32List(
/* 2336 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         
/*      */         case 42:
/* 2340 */           SchemaUtil.writeBoolList(
/* 2341 */               numberAt(pos), (List<Boolean>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 43:
/* 2344 */           SchemaUtil.writeUInt32List(
/* 2345 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 44:
/* 2348 */           SchemaUtil.writeEnumList(
/* 2349 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 45:
/* 2352 */           SchemaUtil.writeSFixed32List(
/* 2353 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 46:
/* 2356 */           SchemaUtil.writeSFixed64List(
/* 2357 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 47:
/* 2360 */           SchemaUtil.writeSInt32List(
/* 2361 */               numberAt(pos), (List<Integer>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 48:
/* 2364 */           SchemaUtil.writeSInt64List(
/* 2365 */               numberAt(pos), (List<Long>)unsafe.getObject(message, offset), writer, true);
/*      */           break;
/*      */         case 49:
/* 2368 */           SchemaUtil.writeGroupList(
/* 2369 */               numberAt(pos), (List)unsafe
/* 2370 */               .getObject(message, offset), writer, 
/*      */               
/* 2372 */               getMessageFieldSchema(pos));
/*      */           break;
/*      */         
/*      */         case 50:
/* 2376 */           writeMapHelper(writer, number, unsafe.getObject(message, offset), pos);
/*      */           break;
/*      */         case 51:
/* 2379 */           if (isOneofPresent(message, number, pos)) {
/* 2380 */             writer.writeDouble(number, oneofDoubleAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 52:
/* 2384 */           if (isOneofPresent(message, number, pos)) {
/* 2385 */             writer.writeFloat(number, oneofFloatAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 53:
/* 2389 */           if (isOneofPresent(message, number, pos)) {
/* 2390 */             writer.writeInt64(number, oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 54:
/* 2394 */           if (isOneofPresent(message, number, pos)) {
/* 2395 */             writer.writeUInt64(number, oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 55:
/* 2399 */           if (isOneofPresent(message, number, pos)) {
/* 2400 */             writer.writeInt32(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 56:
/* 2404 */           if (isOneofPresent(message, number, pos)) {
/* 2405 */             writer.writeFixed64(number, oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 57:
/* 2409 */           if (isOneofPresent(message, number, pos)) {
/* 2410 */             writer.writeFixed32(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 58:
/* 2414 */           if (isOneofPresent(message, number, pos)) {
/* 2415 */             writer.writeBool(number, oneofBooleanAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 59:
/* 2419 */           if (isOneofPresent(message, number, pos)) {
/* 2420 */             writeString(number, unsafe.getObject(message, offset), writer);
/*      */           }
/*      */           break;
/*      */         case 60:
/* 2424 */           if (isOneofPresent(message, number, pos)) {
/* 2425 */             Object value = unsafe.getObject(message, offset);
/* 2426 */             writer.writeMessage(number, value, getMessageFieldSchema(pos));
/*      */           } 
/*      */           break;
/*      */         case 61:
/* 2430 */           if (isOneofPresent(message, number, pos)) {
/* 2431 */             writer.writeBytes(number, (ByteString)unsafe.getObject(message, offset));
/*      */           }
/*      */           break;
/*      */         case 62:
/* 2435 */           if (isOneofPresent(message, number, pos)) {
/* 2436 */             writer.writeUInt32(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 63:
/* 2440 */           if (isOneofPresent(message, number, pos)) {
/* 2441 */             writer.writeEnum(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 64:
/* 2445 */           if (isOneofPresent(message, number, pos)) {
/* 2446 */             writer.writeSFixed32(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 65:
/* 2450 */           if (isOneofPresent(message, number, pos)) {
/* 2451 */             writer.writeSFixed64(number, oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 66:
/* 2455 */           if (isOneofPresent(message, number, pos)) {
/* 2456 */             writer.writeSInt32(number, oneofIntAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 67:
/* 2460 */           if (isOneofPresent(message, number, pos)) {
/* 2461 */             writer.writeSInt64(number, oneofLongAt(message, offset));
/*      */           }
/*      */           break;
/*      */         case 68:
/* 2465 */           if (isOneofPresent(message, number, pos)) {
/* 2466 */             writer.writeGroup(number, unsafe
/* 2467 */                 .getObject(message, offset), getMessageFieldSchema(pos));
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 2475 */     while (nextExtension != null) {
/* 2476 */       this.extensionSchema.serializeExtension(writer, nextExtension);
/* 2477 */       nextExtension = extensionIterator.hasNext() ? extensionIterator.next() : null;
/*      */     } 
/* 2479 */     writeUnknownInMessageTo(this.unknownFieldSchema, message, writer);
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeFieldsInDescendingOrder(T message, Writer writer) throws IOException {
/* 2484 */     writeUnknownInMessageTo(this.unknownFieldSchema, message, writer);
/*      */     
/* 2486 */     Iterator<? extends Map.Entry<?, ?>> extensionIterator = null;
/* 2487 */     Map.Entry<?, ?> nextExtension = null;
/* 2488 */     if (this.hasExtensions) {
/* 2489 */       FieldSet<?> extensions = this.extensionSchema.getExtensions(message);
/* 2490 */       if (!extensions.isEmpty()) {
/* 2491 */         extensionIterator = extensions.descendingIterator();
/* 2492 */         nextExtension = extensionIterator.next();
/*      */       } 
/*      */     } 
/*      */     
/* 2496 */     for (int pos = this.buffer.length - 3; pos >= 0; pos -= 3) {
/* 2497 */       int typeAndOffset = typeAndOffsetAt(pos);
/* 2498 */       int number = numberAt(pos);
/*      */ 
/*      */       
/* 2501 */       while (nextExtension != null && this.extensionSchema.extensionNumber(nextExtension) > number) {
/* 2502 */         this.extensionSchema.serializeExtension(writer, nextExtension);
/* 2503 */         nextExtension = extensionIterator.hasNext() ? extensionIterator.next() : null;
/*      */       } 
/*      */       
/* 2506 */       switch (type(typeAndOffset)) {
/*      */         case 0:
/* 2508 */           if (isFieldPresent(message, pos)) {
/* 2509 */             writer.writeDouble(number, doubleAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 1:
/* 2513 */           if (isFieldPresent(message, pos)) {
/* 2514 */             writer.writeFloat(number, floatAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 2:
/* 2518 */           if (isFieldPresent(message, pos)) {
/* 2519 */             writer.writeInt64(number, longAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 3:
/* 2523 */           if (isFieldPresent(message, pos)) {
/* 2524 */             writer.writeUInt64(number, longAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 4:
/* 2528 */           if (isFieldPresent(message, pos)) {
/* 2529 */             writer.writeInt32(number, intAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 5:
/* 2533 */           if (isFieldPresent(message, pos)) {
/* 2534 */             writer.writeFixed64(number, longAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 6:
/* 2538 */           if (isFieldPresent(message, pos)) {
/* 2539 */             writer.writeFixed32(number, intAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 7:
/* 2543 */           if (isFieldPresent(message, pos)) {
/* 2544 */             writer.writeBool(number, booleanAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 8:
/* 2548 */           if (isFieldPresent(message, pos)) {
/* 2549 */             writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
/*      */           }
/*      */           break;
/*      */         case 9:
/* 2553 */           if (isFieldPresent(message, pos)) {
/* 2554 */             Object value = UnsafeUtil.getObject(message, offset(typeAndOffset));
/* 2555 */             writer.writeMessage(number, value, getMessageFieldSchema(pos));
/*      */           } 
/*      */           break;
/*      */         case 10:
/* 2559 */           if (isFieldPresent(message, pos)) {
/* 2560 */             writer.writeBytes(number, 
/* 2561 */                 (ByteString)UnsafeUtil.getObject(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 11:
/* 2565 */           if (isFieldPresent(message, pos)) {
/* 2566 */             writer.writeUInt32(number, intAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 12:
/* 2570 */           if (isFieldPresent(message, pos)) {
/* 2571 */             writer.writeEnum(number, intAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 13:
/* 2575 */           if (isFieldPresent(message, pos)) {
/* 2576 */             writer.writeSFixed32(number, intAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 14:
/* 2580 */           if (isFieldPresent(message, pos)) {
/* 2581 */             writer.writeSFixed64(number, longAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 15:
/* 2585 */           if (isFieldPresent(message, pos)) {
/* 2586 */             writer.writeSInt32(number, intAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 16:
/* 2590 */           if (isFieldPresent(message, pos)) {
/* 2591 */             writer.writeSInt64(number, longAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 17:
/* 2595 */           if (isFieldPresent(message, pos)) {
/* 2596 */             writer.writeGroup(number, 
/*      */                 
/* 2598 */                 UnsafeUtil.getObject(message, offset(typeAndOffset)), 
/* 2599 */                 getMessageFieldSchema(pos));
/*      */           }
/*      */           break;
/*      */         case 18:
/* 2603 */           SchemaUtil.writeDoubleList(number, 
/*      */               
/* 2605 */               (List<Double>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 19:
/* 2610 */           SchemaUtil.writeFloatList(number, 
/*      */               
/* 2612 */               (List<Float>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 20:
/* 2617 */           SchemaUtil.writeInt64List(number, 
/*      */               
/* 2619 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 21:
/* 2624 */           SchemaUtil.writeUInt64List(number, 
/*      */               
/* 2626 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 22:
/* 2631 */           SchemaUtil.writeInt32List(number, 
/*      */               
/* 2633 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 23:
/* 2638 */           SchemaUtil.writeFixed64List(number, 
/*      */               
/* 2640 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 24:
/* 2645 */           SchemaUtil.writeFixed32List(number, 
/*      */               
/* 2647 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 25:
/* 2652 */           SchemaUtil.writeBoolList(number, 
/*      */               
/* 2654 */               (List<Boolean>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 26:
/* 2659 */           SchemaUtil.writeStringList(number, 
/* 2660 */               (List<String>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
/*      */           break;
/*      */         case 27:
/* 2663 */           SchemaUtil.writeMessageList(number, 
/*      */               
/* 2665 */               (List)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, 
/*      */               
/* 2667 */               getMessageFieldSchema(pos));
/*      */           break;
/*      */         case 28:
/* 2670 */           SchemaUtil.writeBytesList(number, 
/*      */               
/* 2672 */               (List<ByteString>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
/*      */           break;
/*      */         
/*      */         case 29:
/* 2676 */           SchemaUtil.writeUInt32List(number, 
/*      */               
/* 2678 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 30:
/* 2683 */           SchemaUtil.writeEnumList(number, 
/*      */               
/* 2685 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 31:
/* 2690 */           SchemaUtil.writeSFixed32List(number, 
/*      */               
/* 2692 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 32:
/* 2697 */           SchemaUtil.writeSFixed64List(number, 
/*      */               
/* 2699 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 33:
/* 2704 */           SchemaUtil.writeSInt32List(number, 
/*      */               
/* 2706 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 34:
/* 2711 */           SchemaUtil.writeSInt64List(number, 
/*      */               
/* 2713 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, false);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 35:
/* 2718 */           SchemaUtil.writeDoubleList(number, 
/*      */               
/* 2720 */               (List<Double>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 36:
/* 2725 */           SchemaUtil.writeFloatList(number, 
/*      */               
/* 2727 */               (List<Float>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 37:
/* 2732 */           SchemaUtil.writeInt64List(number, 
/*      */               
/* 2734 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 38:
/* 2739 */           SchemaUtil.writeUInt64List(number, 
/*      */               
/* 2741 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 39:
/* 2746 */           SchemaUtil.writeInt32List(number, 
/*      */               
/* 2748 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 40:
/* 2753 */           SchemaUtil.writeFixed64List(number, 
/*      */               
/* 2755 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 41:
/* 2760 */           SchemaUtil.writeFixed32List(number, 
/*      */               
/* 2762 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 42:
/* 2768 */           SchemaUtil.writeBoolList(number, 
/*      */               
/* 2770 */               (List<Boolean>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 43:
/* 2775 */           SchemaUtil.writeUInt32List(number, 
/*      */               
/* 2777 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 44:
/* 2782 */           SchemaUtil.writeEnumList(number, 
/*      */               
/* 2784 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 45:
/* 2789 */           SchemaUtil.writeSFixed32List(number, 
/*      */               
/* 2791 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 46:
/* 2796 */           SchemaUtil.writeSFixed64List(number, 
/*      */               
/* 2798 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 47:
/* 2803 */           SchemaUtil.writeSInt32List(number, 
/*      */               
/* 2805 */               (List<Integer>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 48:
/* 2810 */           SchemaUtil.writeSInt64List(number, 
/*      */               
/* 2812 */               (List<Long>)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, true);
/*      */           break;
/*      */ 
/*      */         
/*      */         case 49:
/* 2817 */           SchemaUtil.writeGroupList(number, 
/*      */               
/* 2819 */               (List)UnsafeUtil.getObject(message, offset(typeAndOffset)), writer, 
/*      */               
/* 2821 */               getMessageFieldSchema(pos));
/*      */           break;
/*      */         
/*      */         case 50:
/* 2825 */           writeMapHelper(writer, number, UnsafeUtil.getObject(message, offset(typeAndOffset)), pos);
/*      */           break;
/*      */         case 51:
/* 2828 */           if (isOneofPresent(message, number, pos)) {
/* 2829 */             writer.writeDouble(number, oneofDoubleAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 52:
/* 2833 */           if (isOneofPresent(message, number, pos)) {
/* 2834 */             writer.writeFloat(number, oneofFloatAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 53:
/* 2838 */           if (isOneofPresent(message, number, pos)) {
/* 2839 */             writer.writeInt64(number, oneofLongAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 54:
/* 2843 */           if (isOneofPresent(message, number, pos)) {
/* 2844 */             writer.writeUInt64(number, oneofLongAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 55:
/* 2848 */           if (isOneofPresent(message, number, pos)) {
/* 2849 */             writer.writeInt32(number, oneofIntAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 56:
/* 2853 */           if (isOneofPresent(message, number, pos)) {
/* 2854 */             writer.writeFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 57:
/* 2858 */           if (isOneofPresent(message, number, pos)) {
/* 2859 */             writer.writeFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 58:
/* 2863 */           if (isOneofPresent(message, number, pos)) {
/* 2864 */             writer.writeBool(number, oneofBooleanAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 59:
/* 2868 */           if (isOneofPresent(message, number, pos)) {
/* 2869 */             writeString(number, UnsafeUtil.getObject(message, offset(typeAndOffset)), writer);
/*      */           }
/*      */           break;
/*      */         case 60:
/* 2873 */           if (isOneofPresent(message, number, pos)) {
/* 2874 */             Object value = UnsafeUtil.getObject(message, offset(typeAndOffset));
/* 2875 */             writer.writeMessage(number, value, getMessageFieldSchema(pos));
/*      */           } 
/*      */           break;
/*      */         case 61:
/* 2879 */           if (isOneofPresent(message, number, pos)) {
/* 2880 */             writer.writeBytes(number, 
/* 2881 */                 (ByteString)UnsafeUtil.getObject(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 62:
/* 2885 */           if (isOneofPresent(message, number, pos)) {
/* 2886 */             writer.writeUInt32(number, oneofIntAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 63:
/* 2890 */           if (isOneofPresent(message, number, pos)) {
/* 2891 */             writer.writeEnum(number, oneofIntAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 64:
/* 2895 */           if (isOneofPresent(message, number, pos)) {
/* 2896 */             writer.writeSFixed32(number, oneofIntAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 65:
/* 2900 */           if (isOneofPresent(message, number, pos)) {
/* 2901 */             writer.writeSFixed64(number, oneofLongAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 66:
/* 2905 */           if (isOneofPresent(message, number, pos)) {
/* 2906 */             writer.writeSInt32(number, oneofIntAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 67:
/* 2910 */           if (isOneofPresent(message, number, pos)) {
/* 2911 */             writer.writeSInt64(number, oneofLongAt(message, offset(typeAndOffset)));
/*      */           }
/*      */           break;
/*      */         case 68:
/* 2915 */           if (isOneofPresent(message, number, pos)) {
/* 2916 */             writer.writeGroup(number, 
/*      */                 
/* 2918 */                 UnsafeUtil.getObject(message, offset(typeAndOffset)), 
/* 2919 */                 getMessageFieldSchema(pos));
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */     
/*      */     } 
/* 2926 */     while (nextExtension != null) {
/* 2927 */       this.extensionSchema.serializeExtension(writer, nextExtension);
/* 2928 */       nextExtension = extensionIterator.hasNext() ? extensionIterator.next() : null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private <K, V> void writeMapHelper(Writer writer, int number, Object mapField, int pos) throws IOException {
/* 2935 */     if (mapField != null) {
/* 2936 */       writer.writeMap(number, this.mapFieldSchema
/*      */           
/* 2938 */           .forMapMetadata(getMapFieldDefaultEntry(pos)), this.mapFieldSchema
/* 2939 */           .forMapData(mapField));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private <UT, UB> void writeUnknownInMessageTo(UnknownFieldSchema<UT, UB> schema, T message, Writer writer) throws IOException {
/* 2945 */     schema.writeTo(schema.getFromMessage(message), writer);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void mergeFrom(T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 2951 */     if (extensionRegistry == null) {
/* 2952 */       throw new NullPointerException();
/*      */     }
/* 2954 */     checkMutable(message);
/* 2955 */     mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, message, reader, extensionRegistry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <UT, UB, ET extends FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(UnknownFieldSchema<UT, UB> unknownFieldSchema, ExtensionSchema<ET> extensionSchema, T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 2969 */     UB unknownFields = null;
/* 2970 */     FieldSet<ET> extensions = null;
/*      */     try {
/*      */       while (true) {
/* 2973 */         int number = reader.getFieldNumber();
/* 2974 */         int pos = positionForFieldNumber(number);
/* 2975 */         if (pos < 0) {
/* 2976 */           if (number == Integer.MAX_VALUE) {
/*      */             return;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2983 */           Object extension = !this.hasExtensions ? null : extensionSchema.findExtensionByNumber(extensionRegistry, this.defaultInstance, number);
/*      */           
/* 2985 */           if (extension != null) {
/* 2986 */             if (extensions == null) {
/* 2987 */               extensions = extensionSchema.getMutableExtensions(message);
/*      */             }
/*      */             
/* 2990 */             unknownFields = extensionSchema.parseExtension(message, reader, extension, extensionRegistry, extensions, unknownFields, unknownFieldSchema);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 3000 */           if (unknownFieldSchema.shouldDiscardUnknownFields(reader)) {
/* 3001 */             if (reader.skipField()) {
/*      */               continue;
/*      */             }
/*      */           } else {
/* 3005 */             if (unknownFields == null) {
/* 3006 */               unknownFields = unknownFieldSchema.getBuilderFromMessage(message);
/*      */             }
/*      */             
/* 3009 */             if (unknownFieldSchema.mergeOneFieldFrom(unknownFields, reader, 0)) {
/*      */               continue;
/*      */             }
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 3017 */         int typeAndOffset = typeAndOffsetAt(pos); try {
/*      */           MessageLite messageLite3; int j; MessageLite messageLite2; List<Integer> enumList; MessageLite messageLite1; int enumValue; MessageLite current;
/*      */           Internal.EnumVerifier enumVerifier;
/* 3020 */           switch (type(typeAndOffset)) {
/*      */             case 0:
/* 3022 */               UnsafeUtil.putDouble(message, offset(typeAndOffset), reader.readDouble());
/* 3023 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 1:
/* 3026 */               UnsafeUtil.putFloat(message, offset(typeAndOffset), reader.readFloat());
/* 3027 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 2:
/* 3030 */               UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readInt64());
/* 3031 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 3:
/* 3034 */               UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readUInt64());
/* 3035 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 4:
/* 3038 */               UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readInt32());
/* 3039 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 5:
/* 3042 */               UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readFixed64());
/* 3043 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 6:
/* 3046 */               UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readFixed32());
/* 3047 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 7:
/* 3050 */               UnsafeUtil.putBoolean(message, offset(typeAndOffset), reader.readBool());
/* 3051 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 8:
/* 3054 */               readString(message, typeAndOffset, reader);
/* 3055 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             
/*      */             case 9:
/* 3059 */               messageLite3 = (MessageLite)mutableMessageFieldForMerge(message, pos);
/* 3060 */               reader.mergeMessageField(messageLite3, 
/* 3061 */                   getMessageFieldSchema(pos), extensionRegistry);
/* 3062 */               storeMessageField(message, pos, messageLite3);
/*      */               continue;
/*      */             
/*      */             case 10:
/* 3066 */               UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readBytes());
/* 3067 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 11:
/* 3070 */               UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readUInt32());
/* 3071 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             
/*      */             case 12:
/* 3075 */               j = reader.readEnum();
/* 3076 */               enumVerifier = getEnumFieldVerifier(pos);
/* 3077 */               if (enumVerifier == null || enumVerifier.isInRange(j)) {
/* 3078 */                 UnsafeUtil.putInt(message, offset(typeAndOffset), j);
/* 3079 */                 setFieldPresent(message, pos);
/*      */                 continue;
/*      */               } 
/* 3082 */               unknownFields = SchemaUtil.storeUnknownEnum(message, number, j, unknownFields, unknownFieldSchema);
/*      */               continue;
/*      */ 
/*      */ 
/*      */             
/*      */             case 13:
/* 3088 */               UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readSFixed32());
/* 3089 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 14:
/* 3092 */               UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readSFixed64());
/* 3093 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 15:
/* 3096 */               UnsafeUtil.putInt(message, offset(typeAndOffset), reader.readSInt32());
/* 3097 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             case 16:
/* 3100 */               UnsafeUtil.putLong(message, offset(typeAndOffset), reader.readSInt64());
/* 3101 */               setFieldPresent(message, pos);
/*      */               continue;
/*      */             
/*      */             case 17:
/* 3105 */               messageLite2 = (MessageLite)mutableMessageFieldForMerge(message, pos);
/* 3106 */               reader.mergeGroupField(messageLite2, 
/* 3107 */                   getMessageFieldSchema(pos), extensionRegistry);
/* 3108 */               storeMessageField(message, pos, messageLite2);
/*      */               continue;
/*      */             
/*      */             case 18:
/* 3112 */               reader.readDoubleList(this.listFieldSchema
/* 3113 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 19:
/* 3116 */               reader.readFloatList(this.listFieldSchema
/* 3117 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 20:
/* 3120 */               reader.readInt64List(this.listFieldSchema
/* 3121 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 21:
/* 3124 */               reader.readUInt64List(this.listFieldSchema
/* 3125 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 22:
/* 3128 */               reader.readInt32List(this.listFieldSchema
/* 3129 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 23:
/* 3132 */               reader.readFixed64List(this.listFieldSchema
/* 3133 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 24:
/* 3136 */               reader.readFixed32List(this.listFieldSchema
/* 3137 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 25:
/* 3140 */               reader.readBoolList(this.listFieldSchema
/* 3141 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 26:
/* 3144 */               readStringList(message, typeAndOffset, reader);
/*      */               continue;
/*      */             
/*      */             case 27:
/* 3148 */               readMessageList(message, typeAndOffset, reader, 
/*      */ 
/*      */ 
/*      */                   
/* 3152 */                   getMessageFieldSchema(pos), extensionRegistry);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 28:
/* 3157 */               reader.readBytesList(this.listFieldSchema
/* 3158 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 29:
/* 3161 */               reader.readUInt32List(this.listFieldSchema
/* 3162 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 30:
/* 3167 */               enumList = this.listFieldSchema.mutableListAt(message, offset(typeAndOffset));
/* 3168 */               reader.readEnumList(enumList);
/*      */               
/* 3170 */               unknownFields = SchemaUtil.filterUnknownEnumList(message, number, enumList, 
/*      */ 
/*      */ 
/*      */                   
/* 3174 */                   getEnumFieldVerifier(pos), unknownFields, unknownFieldSchema);
/*      */               continue;
/*      */ 
/*      */ 
/*      */             
/*      */             case 31:
/* 3180 */               reader.readSFixed32List(this.listFieldSchema
/* 3181 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 32:
/* 3184 */               reader.readSFixed64List(this.listFieldSchema
/* 3185 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 33:
/* 3188 */               reader.readSInt32List(this.listFieldSchema
/* 3189 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 34:
/* 3192 */               reader.readSInt64List(this.listFieldSchema
/* 3193 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 35:
/* 3196 */               reader.readDoubleList(this.listFieldSchema
/* 3197 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 36:
/* 3200 */               reader.readFloatList(this.listFieldSchema
/* 3201 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 37:
/* 3204 */               reader.readInt64List(this.listFieldSchema
/* 3205 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 38:
/* 3208 */               reader.readUInt64List(this.listFieldSchema
/* 3209 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 39:
/* 3212 */               reader.readInt32List(this.listFieldSchema
/* 3213 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 40:
/* 3216 */               reader.readFixed64List(this.listFieldSchema
/* 3217 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 41:
/* 3220 */               reader.readFixed32List(this.listFieldSchema
/* 3221 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 42:
/* 3224 */               reader.readBoolList(this.listFieldSchema
/* 3225 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 43:
/* 3228 */               reader.readUInt32List(this.listFieldSchema
/* 3229 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 44:
/* 3234 */               enumList = this.listFieldSchema.mutableListAt(message, offset(typeAndOffset));
/* 3235 */               reader.readEnumList(enumList);
/*      */               
/* 3237 */               unknownFields = SchemaUtil.filterUnknownEnumList(message, number, enumList, 
/*      */ 
/*      */ 
/*      */                   
/* 3241 */                   getEnumFieldVerifier(pos), unknownFields, unknownFieldSchema);
/*      */               continue;
/*      */ 
/*      */ 
/*      */             
/*      */             case 45:
/* 3247 */               reader.readSFixed32List(this.listFieldSchema
/* 3248 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 46:
/* 3251 */               reader.readSFixed64List(this.listFieldSchema
/* 3252 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 47:
/* 3255 */               reader.readSInt32List(this.listFieldSchema
/* 3256 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             case 48:
/* 3259 */               reader.readSInt64List(this.listFieldSchema
/* 3260 */                   .mutableListAt(message, offset(typeAndOffset)));
/*      */               continue;
/*      */             
/*      */             case 49:
/* 3264 */               readGroupList(message, 
/*      */                   
/* 3266 */                   offset(typeAndOffset), reader, 
/*      */                   
/* 3268 */                   getMessageFieldSchema(pos), extensionRegistry);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 50:
/* 3273 */               mergeMap(message, pos, getMapFieldDefaultEntry(pos), extensionRegistry, reader);
/*      */               continue;
/*      */             case 51:
/* 3276 */               UnsafeUtil.putObject(message, 
/* 3277 */                   offset(typeAndOffset), Double.valueOf(reader.readDouble()));
/* 3278 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 52:
/* 3281 */               UnsafeUtil.putObject(message, 
/* 3282 */                   offset(typeAndOffset), Float.valueOf(reader.readFloat()));
/* 3283 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 53:
/* 3286 */               UnsafeUtil.putObject(message, 
/* 3287 */                   offset(typeAndOffset), Long.valueOf(reader.readInt64()));
/* 3288 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 54:
/* 3291 */               UnsafeUtil.putObject(message, 
/* 3292 */                   offset(typeAndOffset), Long.valueOf(reader.readUInt64()));
/* 3293 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 55:
/* 3296 */               UnsafeUtil.putObject(message, 
/* 3297 */                   offset(typeAndOffset), Integer.valueOf(reader.readInt32()));
/* 3298 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 56:
/* 3301 */               UnsafeUtil.putObject(message, 
/* 3302 */                   offset(typeAndOffset), Long.valueOf(reader.readFixed64()));
/* 3303 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 57:
/* 3306 */               UnsafeUtil.putObject(message, 
/* 3307 */                   offset(typeAndOffset), Integer.valueOf(reader.readFixed32()));
/* 3308 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 58:
/* 3311 */               UnsafeUtil.putObject(message, 
/* 3312 */                   offset(typeAndOffset), Boolean.valueOf(reader.readBool()));
/* 3313 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 59:
/* 3316 */               readString(message, typeAndOffset, reader);
/* 3317 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 60:
/* 3322 */               messageLite1 = (MessageLite)mutableOneofMessageFieldForMerge(message, number, pos);
/* 3323 */               reader.mergeMessageField(messageLite1, 
/* 3324 */                   getMessageFieldSchema(pos), extensionRegistry);
/* 3325 */               storeOneofMessageField(message, number, pos, messageLite1);
/*      */               continue;
/*      */             
/*      */             case 61:
/* 3329 */               UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readBytes());
/* 3330 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 62:
/* 3333 */               UnsafeUtil.putObject(message, 
/* 3334 */                   offset(typeAndOffset), Integer.valueOf(reader.readUInt32()));
/* 3335 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             
/*      */             case 63:
/* 3339 */               enumValue = reader.readEnum();
/* 3340 */               enumVerifier = getEnumFieldVerifier(pos);
/* 3341 */               if (enumVerifier == null || enumVerifier.isInRange(enumValue)) {
/* 3342 */                 UnsafeUtil.putObject(message, offset(typeAndOffset), Integer.valueOf(enumValue));
/* 3343 */                 setOneofPresent(message, number, pos);
/*      */                 continue;
/*      */               } 
/* 3346 */               unknownFields = SchemaUtil.storeUnknownEnum(message, number, enumValue, unknownFields, unknownFieldSchema);
/*      */               continue;
/*      */ 
/*      */ 
/*      */             
/*      */             case 64:
/* 3352 */               UnsafeUtil.putObject(message, 
/* 3353 */                   offset(typeAndOffset), Integer.valueOf(reader.readSFixed32()));
/* 3354 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 65:
/* 3357 */               UnsafeUtil.putObject(message, 
/* 3358 */                   offset(typeAndOffset), Long.valueOf(reader.readSFixed64()));
/* 3359 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 66:
/* 3362 */               UnsafeUtil.putObject(message, 
/* 3363 */                   offset(typeAndOffset), Integer.valueOf(reader.readSInt32()));
/* 3364 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */             case 67:
/* 3367 */               UnsafeUtil.putObject(message, 
/* 3368 */                   offset(typeAndOffset), Long.valueOf(reader.readSInt64()));
/* 3369 */               setOneofPresent(message, number, pos);
/*      */               continue;
/*      */ 
/*      */             
/*      */             case 68:
/* 3374 */               current = (MessageLite)mutableOneofMessageFieldForMerge(message, number, pos);
/* 3375 */               reader.mergeGroupField(current, 
/* 3376 */                   getMessageFieldSchema(pos), extensionRegistry);
/* 3377 */               storeOneofMessageField(message, number, pos, current);
/*      */               continue;
/*      */           } 
/*      */ 
/*      */           
/* 3382 */           if (unknownFields == null) {
/* 3383 */             unknownFields = unknownFieldSchema.getBuilderFromMessage(message);
/*      */           }
/* 3385 */           if (!unknownFieldSchema.mergeOneFieldFrom(unknownFields, reader, 0))
/*      */           {
/*      */             return;
/*      */           
/*      */           }
/*      */         }
/* 3391 */         catch (InvalidWireTypeException e) {
/*      */ 
/*      */           
/* 3394 */           if (unknownFieldSchema.shouldDiscardUnknownFields(reader)) {
/* 3395 */             if (!reader.skipField())
/*      */               return; 
/*      */             continue;
/*      */           } 
/* 3399 */           if (unknownFields == null) {
/* 3400 */             unknownFields = unknownFieldSchema.getBuilderFromMessage(message);
/*      */           }
/* 3402 */           if (!unknownFieldSchema.mergeOneFieldFrom(unknownFields, reader, 0)) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*      */       return;
/*      */     } finally {
/* 3410 */       for (int i = this.checkInitializedCount; i < this.repeatedFieldOffsetStart; i++)
/*      */       {
/* 3412 */         unknownFields = filterMapUnknownEnumValues(message, this.intArray[i], unknownFields, unknownFieldSchema, message);
/*      */       }
/*      */       
/* 3415 */       if (unknownFields != null) {
/* 3416 */         unknownFieldSchema.setBuilderToMessage(message, unknownFields);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static UnknownFieldSetLite getMutableUnknownFields(Object message) {
/* 3425 */     UnknownFieldSetLite unknownFields = ((GeneratedMessageLite)message).unknownFields;
/* 3426 */     if (unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
/* 3427 */       unknownFields = UnknownFieldSetLite.newInstance();
/* 3428 */       ((GeneratedMessageLite)message).unknownFields = unknownFields;
/*      */     } 
/* 3430 */     return unknownFields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int decodeMapEntryValue(byte[] data, int position, int limit, WireFormat.FieldType fieldType, Class<?> messageType, ArrayDecoders.Registers registers) throws IOException {
/* 3442 */     switch (fieldType) {
/*      */       case BOOL:
/* 3444 */         position = ArrayDecoders.decodeVarint64(data, position, registers);
/* 3445 */         registers.object1 = Boolean.valueOf((registers.long1 != 0L));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3498 */         return position;case BYTES: position = ArrayDecoders.decodeBytes(data, position, registers); return position;case DOUBLE: registers.object1 = Double.valueOf(ArrayDecoders.decodeDouble(data, position)); position += 8; return position;case FIXED32: case SFIXED32: registers.object1 = Integer.valueOf(ArrayDecoders.decodeFixed32(data, position)); position += 4; return position;case FIXED64: case SFIXED64: registers.object1 = Long.valueOf(ArrayDecoders.decodeFixed64(data, position)); position += 8; return position;case FLOAT: registers.object1 = Float.valueOf(ArrayDecoders.decodeFloat(data, position)); position += 4; return position;case ENUM: case INT32: case UINT32: position = ArrayDecoders.decodeVarint32(data, position, registers); registers.object1 = Integer.valueOf(registers.int1); return position;case INT64: case UINT64: position = ArrayDecoders.decodeVarint64(data, position, registers); registers.object1 = Long.valueOf(registers.long1); return position;case MESSAGE: position = ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor(messageType), data, position, limit, registers); return position;case SINT32: position = ArrayDecoders.decodeVarint32(data, position, registers); registers.object1 = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)); return position;case SINT64: position = ArrayDecoders.decodeVarint64(data, position, registers); registers.object1 = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)); return position;case STRING: position = ArrayDecoders.decodeStringRequireUtf8(data, position, registers); return position;
/*      */     } 
/*      */     throw new RuntimeException("unsupported field type.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <K, V> int decodeMapEntry(byte[] data, int position, int limit, MapEntryLite.Metadata<K, V> metadata, Map<K, V> target, ArrayDecoders.Registers registers) throws IOException {
/* 3510 */     position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 3511 */     int length = registers.int1;
/* 3512 */     if (length < 0 || length > limit - position) {
/* 3513 */       throw InvalidProtocolBufferException.truncatedMessage();
/*      */     }
/* 3515 */     int end = position + length;
/* 3516 */     K key = metadata.defaultKey;
/* 3517 */     V value = metadata.defaultValue;
/* 3518 */     while (position < end) {
/* 3519 */       int tag = data[position++];
/* 3520 */       if (tag < 0) {
/* 3521 */         position = ArrayDecoders.decodeVarint32(tag, data, position, registers);
/* 3522 */         tag = registers.int1;
/*      */       } 
/* 3524 */       int fieldNumber = tag >>> 3;
/* 3525 */       int wireType = tag & 0x7;
/* 3526 */       switch (fieldNumber) {
/*      */         case 1:
/* 3528 */           if (wireType == metadata.keyType.getWireType()) {
/*      */             
/* 3530 */             position = decodeMapEntryValue(data, position, limit, metadata.keyType, null, registers);
/* 3531 */             key = (K)registers.object1;
/*      */             continue;
/*      */           } 
/*      */           break;
/*      */         case 2:
/* 3536 */           if (wireType == metadata.valueType.getWireType()) {
/*      */             
/* 3538 */             position = decodeMapEntryValue(data, position, limit, metadata.valueType, metadata.defaultValue
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 3543 */                 .getClass(), registers);
/*      */             
/* 3545 */             value = (V)registers.object1;
/*      */             continue;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 3552 */       position = ArrayDecoders.skipField(tag, data, position, limit, registers);
/*      */     } 
/* 3554 */     if (position != end) {
/* 3555 */       throw InvalidProtocolBufferException.parseFailure();
/*      */     }
/* 3557 */     target.put(key, value);
/* 3558 */     return end;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parseRepeatedField(T message, byte[] data, int position, int limit, int tag, int number, int wireType, int bufferPosition, long typeAndOffset, int fieldType, long fieldOffset, ArrayDecoders.Registers registers) throws IOException {
/* 3576 */     Internal.ProtobufList<?> list = (Internal.ProtobufList)UNSAFE.getObject(message, fieldOffset);
/* 3577 */     if (!list.isModifiable()) {
/* 3578 */       int size = list.size();
/* 3579 */       list = list.mutableCopyWithCapacity(size * 2);
/* 3580 */       UNSAFE.putObject(message, fieldOffset, list);
/*      */     } 
/* 3582 */     switch (fieldType) {
/*      */       case 18:
/*      */       case 35:
/* 3585 */         if (wireType == 2) {
/* 3586 */           position = ArrayDecoders.decodePackedDoubleList(data, position, list, registers); break;
/* 3587 */         }  if (wireType == 1) {
/* 3588 */           position = ArrayDecoders.decodeDoubleList(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 19:
/*      */       case 36:
/* 3593 */         if (wireType == 2) {
/* 3594 */           position = ArrayDecoders.decodePackedFloatList(data, position, list, registers); break;
/* 3595 */         }  if (wireType == 5) {
/* 3596 */           position = ArrayDecoders.decodeFloatList(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 20:
/*      */       case 21:
/*      */       case 37:
/*      */       case 38:
/* 3603 */         if (wireType == 2) {
/* 3604 */           position = ArrayDecoders.decodePackedVarint64List(data, position, list, registers); break;
/* 3605 */         }  if (wireType == 0) {
/* 3606 */           position = ArrayDecoders.decodeVarint64List(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 22:
/*      */       case 29:
/*      */       case 39:
/*      */       case 43:
/* 3613 */         if (wireType == 2) {
/* 3614 */           position = ArrayDecoders.decodePackedVarint32List(data, position, list, registers); break;
/* 3615 */         }  if (wireType == 0) {
/* 3616 */           position = ArrayDecoders.decodeVarint32List(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 23:
/*      */       case 32:
/*      */       case 40:
/*      */       case 46:
/* 3623 */         if (wireType == 2) {
/* 3624 */           position = ArrayDecoders.decodePackedFixed64List(data, position, list, registers); break;
/* 3625 */         }  if (wireType == 1) {
/* 3626 */           position = ArrayDecoders.decodeFixed64List(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 24:
/*      */       case 31:
/*      */       case 41:
/*      */       case 45:
/* 3633 */         if (wireType == 2) {
/* 3634 */           position = ArrayDecoders.decodePackedFixed32List(data, position, list, registers); break;
/* 3635 */         }  if (wireType == 5) {
/* 3636 */           position = ArrayDecoders.decodeFixed32List(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 25:
/*      */       case 42:
/* 3641 */         if (wireType == 2) {
/* 3642 */           position = ArrayDecoders.decodePackedBoolList(data, position, list, registers); break;
/* 3643 */         }  if (wireType == 0) {
/* 3644 */           position = ArrayDecoders.decodeBoolList(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 26:
/* 3648 */         if (wireType == 2) {
/* 3649 */           if ((typeAndOffset & 0x20000000L) == 0L) {
/* 3650 */             position = ArrayDecoders.decodeStringList(tag, data, position, limit, list, registers); break;
/*      */           } 
/* 3652 */           position = ArrayDecoders.decodeStringListRequireUtf8(tag, data, position, limit, list, registers);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 27:
/* 3657 */         if (wireType == 2)
/*      */         {
/* 3659 */           position = ArrayDecoders.decodeMessageList(
/* 3660 */               getMessageFieldSchema(bufferPosition), tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 28:
/* 3670 */         if (wireType == 2) {
/* 3671 */           position = ArrayDecoders.decodeBytesList(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 30:
/*      */       case 44:
/* 3676 */         if (wireType == 2) {
/* 3677 */           position = ArrayDecoders.decodePackedVarint32List(data, position, list, registers);
/* 3678 */         } else if (wireType == 0) {
/* 3679 */           position = ArrayDecoders.decodeVarint32List(tag, data, position, limit, list, registers);
/*      */         } else {
/*      */           break;
/*      */         } 
/* 3683 */         SchemaUtil.filterUnknownEnumList(message, number, (List)list, 
/*      */ 
/*      */ 
/*      */             
/* 3687 */             getEnumFieldVerifier(bufferPosition), (Object)null, this.unknownFieldSchema);
/*      */         break;
/*      */ 
/*      */       
/*      */       case 33:
/*      */       case 47:
/* 3693 */         if (wireType == 2) {
/* 3694 */           position = ArrayDecoders.decodePackedSInt32List(data, position, list, registers); break;
/* 3695 */         }  if (wireType == 0) {
/* 3696 */           position = ArrayDecoders.decodeSInt32List(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 34:
/*      */       case 48:
/* 3701 */         if (wireType == 2) {
/* 3702 */           position = ArrayDecoders.decodePackedSInt64List(data, position, list, registers); break;
/* 3703 */         }  if (wireType == 0) {
/* 3704 */           position = ArrayDecoders.decodeSInt64List(tag, data, position, limit, list, registers);
/*      */         }
/*      */         break;
/*      */       case 49:
/* 3708 */         if (wireType == 3)
/*      */         {
/* 3710 */           position = ArrayDecoders.decodeGroupList(
/* 3711 */               getMessageFieldSchema(bufferPosition), tag, data, position, limit, (Internal.ProtobufList)list, registers);
/*      */         }
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3723 */     return position;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <K, V> int parseMapField(T message, byte[] data, int position, int limit, int bufferPosition, long fieldOffset, ArrayDecoders.Registers registers) throws IOException {
/* 3735 */     Unsafe unsafe = UNSAFE;
/* 3736 */     Object mapDefaultEntry = getMapFieldDefaultEntry(bufferPosition);
/* 3737 */     Object mapField = unsafe.getObject(message, fieldOffset);
/* 3738 */     if (this.mapFieldSchema.isImmutable(mapField)) {
/* 3739 */       Object oldMapField = mapField;
/* 3740 */       mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
/* 3741 */       this.mapFieldSchema.mergeFrom(mapField, oldMapField);
/* 3742 */       unsafe.putObject(message, fieldOffset, mapField);
/*      */     } 
/* 3744 */     return decodeMapEntry(data, position, limit, this.mapFieldSchema
/*      */ 
/*      */ 
/*      */         
/* 3748 */         .forMapMetadata(mapDefaultEntry), this.mapFieldSchema
/* 3749 */         .forMutableMapData(mapField), registers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int parseOneofField(T message, byte[] data, int position, int limit, int tag, int number, int wireType, int typeAndOffset, int fieldType, long fieldOffset, int bufferPosition, ArrayDecoders.Registers registers) throws IOException {
/* 3767 */     Unsafe unsafe = UNSAFE;
/* 3768 */     long oneofCaseOffset = (this.buffer[bufferPosition + 2] & 0xFFFFF);
/* 3769 */     switch (fieldType) {
/*      */       case 51:
/* 3771 */         if (wireType == 1) {
/* 3772 */           unsafe.putObject(message, fieldOffset, Double.valueOf(ArrayDecoders.decodeDouble(data, position)));
/* 3773 */           position += 8;
/* 3774 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 52:
/* 3778 */         if (wireType == 5) {
/* 3779 */           unsafe.putObject(message, fieldOffset, Float.valueOf(ArrayDecoders.decodeFloat(data, position)));
/* 3780 */           position += 4;
/* 3781 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 53:
/*      */       case 54:
/* 3786 */         if (wireType == 0) {
/* 3787 */           position = ArrayDecoders.decodeVarint64(data, position, registers);
/* 3788 */           unsafe.putObject(message, fieldOffset, Long.valueOf(registers.long1));
/* 3789 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 55:
/*      */       case 62:
/* 3794 */         if (wireType == 0) {
/* 3795 */           position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 3796 */           unsafe.putObject(message, fieldOffset, Integer.valueOf(registers.int1));
/* 3797 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 56:
/*      */       case 65:
/* 3802 */         if (wireType == 1) {
/* 3803 */           unsafe.putObject(message, fieldOffset, Long.valueOf(ArrayDecoders.decodeFixed64(data, position)));
/* 3804 */           position += 8;
/* 3805 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 57:
/*      */       case 64:
/* 3810 */         if (wireType == 5) {
/* 3811 */           unsafe.putObject(message, fieldOffset, Integer.valueOf(ArrayDecoders.decodeFixed32(data, position)));
/* 3812 */           position += 4;
/* 3813 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 58:
/* 3817 */         if (wireType == 0) {
/* 3818 */           position = ArrayDecoders.decodeVarint64(data, position, registers);
/* 3819 */           unsafe.putObject(message, fieldOffset, Boolean.valueOf((registers.long1 != 0L)));
/* 3820 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 59:
/* 3824 */         if (wireType == 2) {
/* 3825 */           position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 3826 */           int length = registers.int1;
/* 3827 */           if (length == 0) {
/* 3828 */             unsafe.putObject(message, fieldOffset, "");
/*      */           } else {
/* 3830 */             if ((typeAndOffset & 0x20000000) != 0 && 
/* 3831 */               !Utf8.isValidUtf8(data, position, position + length)) {
/* 3832 */               throw InvalidProtocolBufferException.invalidUtf8();
/*      */             }
/* 3834 */             String value = new String(data, position, length, Internal.UTF_8);
/* 3835 */             unsafe.putObject(message, fieldOffset, value);
/* 3836 */             position += length;
/*      */           } 
/* 3838 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 60:
/* 3842 */         if (wireType == 2) {
/* 3843 */           Object current = mutableOneofMessageFieldForMerge(message, number, bufferPosition);
/*      */           
/* 3845 */           position = ArrayDecoders.mergeMessageField(current, 
/* 3846 */               getMessageFieldSchema(bufferPosition), data, position, limit, registers);
/* 3847 */           storeOneofMessageField(message, number, bufferPosition, current);
/*      */         } 
/*      */         break;
/*      */       case 61:
/* 3851 */         if (wireType == 2) {
/* 3852 */           position = ArrayDecoders.decodeBytes(data, position, registers);
/* 3853 */           unsafe.putObject(message, fieldOffset, registers.object1);
/* 3854 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 63:
/* 3858 */         if (wireType == 0) {
/* 3859 */           position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 3860 */           int enumValue = registers.int1;
/* 3861 */           Internal.EnumVerifier enumVerifier = getEnumFieldVerifier(bufferPosition);
/* 3862 */           if (enumVerifier == null || enumVerifier.isInRange(enumValue)) {
/* 3863 */             unsafe.putObject(message, fieldOffset, Integer.valueOf(enumValue));
/* 3864 */             unsafe.putInt(message, oneofCaseOffset, number);
/*      */             break;
/*      */           } 
/* 3867 */           getMutableUnknownFields(message).storeField(tag, Long.valueOf(enumValue));
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 66:
/* 3872 */         if (wireType == 0) {
/* 3873 */           position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 3874 */           unsafe.putObject(message, fieldOffset, Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)));
/* 3875 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 67:
/* 3879 */         if (wireType == 0) {
/* 3880 */           position = ArrayDecoders.decodeVarint64(data, position, registers);
/* 3881 */           unsafe.putObject(message, fieldOffset, Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)));
/* 3882 */           unsafe.putInt(message, oneofCaseOffset, number);
/*      */         } 
/*      */         break;
/*      */       case 68:
/* 3886 */         if (wireType == 3) {
/* 3887 */           Object current = mutableOneofMessageFieldForMerge(message, number, bufferPosition);
/* 3888 */           int endTag = tag & 0xFFFFFFF8 | 0x4;
/*      */           
/* 3890 */           position = ArrayDecoders.mergeGroupField(current, 
/*      */               
/* 3892 */               getMessageFieldSchema(bufferPosition), data, position, limit, endTag, registers);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3898 */           storeOneofMessageField(message, number, bufferPosition, current);
/*      */         } 
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 3904 */     return position;
/*      */   }
/*      */   
/*      */   private Schema getMessageFieldSchema(int pos) {
/* 3908 */     int index = pos / 3 * 2;
/* 3909 */     Schema<?> schema = (Schema)this.objects[index];
/* 3910 */     if (schema != null) {
/* 3911 */       return schema;
/*      */     }
/* 3913 */     schema = Protobuf.getInstance().schemaFor((Class)this.objects[index + 1]);
/* 3914 */     this.objects[index] = schema;
/* 3915 */     return schema;
/*      */   }
/*      */   
/*      */   private Object getMapFieldDefaultEntry(int pos) {
/* 3919 */     return this.objects[pos / 3 * 2];
/*      */   }
/*      */   
/*      */   private Internal.EnumVerifier getEnumFieldVerifier(int pos) {
/* 3923 */     return (Internal.EnumVerifier)this.objects[pos / 3 * 2 + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CanIgnoreReturnValue
/*      */   int parseMessage(T message, byte[] data, int position, int limit, int endDelimited, ArrayDecoders.Registers registers) throws IOException {
/* 3936 */     checkMutable(message);
/* 3937 */     Unsafe unsafe = UNSAFE;
/* 3938 */     int currentPresenceFieldOffset = 1048575;
/* 3939 */     int currentPresenceField = 0;
/* 3940 */     int tag = 0;
/* 3941 */     int oldNumber = -1;
/* 3942 */     int pos = 0;
/* 3943 */     while (position < limit) {
/* 3944 */       tag = data[position++];
/* 3945 */       if (tag < 0) {
/* 3946 */         position = ArrayDecoders.decodeVarint32(tag, data, position, registers);
/* 3947 */         tag = registers.int1;
/*      */       } 
/* 3949 */       int number = tag >>> 3;
/* 3950 */       int wireType = tag & 0x7;
/* 3951 */       if (number > oldNumber) {
/* 3952 */         pos = positionForFieldNumber(number, pos / 3);
/*      */       } else {
/* 3954 */         pos = positionForFieldNumber(number);
/*      */       } 
/* 3956 */       oldNumber = number;
/* 3957 */       if (pos == -1) {
/*      */         
/* 3959 */         pos = 0;
/*      */       } else {
/* 3961 */         int typeAndOffset = this.buffer[pos + 1];
/* 3962 */         int fieldType = type(typeAndOffset);
/* 3963 */         long fieldOffset = offset(typeAndOffset);
/* 3964 */         if (fieldType <= 17) {
/*      */           
/* 3966 */           int presenceMaskAndOffset = this.buffer[pos + 2];
/* 3967 */           int presenceMask = 1 << presenceMaskAndOffset >>> 20;
/* 3968 */           int presenceFieldOffset = presenceMaskAndOffset & 0xFFFFF;
/*      */ 
/*      */           
/* 3971 */           if (presenceFieldOffset != currentPresenceFieldOffset) {
/* 3972 */             if (currentPresenceFieldOffset != 1048575) {
/* 3973 */               unsafe.putInt(message, currentPresenceFieldOffset, currentPresenceField);
/*      */             }
/* 3975 */             currentPresenceFieldOffset = presenceFieldOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3981 */             currentPresenceField = (presenceFieldOffset == 1048575) ? 0 : unsafe.getInt(message, presenceFieldOffset);
/*      */           } 
/* 3983 */           switch (fieldType) {
/*      */             case 0:
/* 3985 */               if (wireType == 1) {
/* 3986 */                 UnsafeUtil.putDouble(message, fieldOffset, ArrayDecoders.decodeDouble(data, position));
/* 3987 */                 position += 8;
/* 3988 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 1:
/* 3993 */               if (wireType == 5) {
/* 3994 */                 UnsafeUtil.putFloat(message, fieldOffset, ArrayDecoders.decodeFloat(data, position));
/* 3995 */                 position += 4;
/* 3996 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 2:
/*      */             case 3:
/* 4002 */               if (wireType == 0) {
/* 4003 */                 position = ArrayDecoders.decodeVarint64(data, position, registers);
/* 4004 */                 unsafe.putLong(message, fieldOffset, registers.long1);
/* 4005 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 4:
/*      */             case 11:
/* 4011 */               if (wireType == 0) {
/* 4012 */                 position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 4013 */                 unsafe.putInt(message, fieldOffset, registers.int1);
/* 4014 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 5:
/*      */             case 14:
/* 4020 */               if (wireType == 1) {
/* 4021 */                 unsafe.putLong(message, fieldOffset, ArrayDecoders.decodeFixed64(data, position));
/* 4022 */                 position += 8;
/* 4023 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 6:
/*      */             case 13:
/* 4029 */               if (wireType == 5) {
/* 4030 */                 unsafe.putInt(message, fieldOffset, ArrayDecoders.decodeFixed32(data, position));
/* 4031 */                 position += 4;
/* 4032 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 7:
/* 4037 */               if (wireType == 0) {
/* 4038 */                 position = ArrayDecoders.decodeVarint64(data, position, registers);
/* 4039 */                 UnsafeUtil.putBoolean(message, fieldOffset, (registers.long1 != 0L));
/* 4040 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 8:
/* 4045 */               if (wireType == 2) {
/* 4046 */                 if (isEnforceUtf8(typeAndOffset)) {
/* 4047 */                   position = ArrayDecoders.decodeStringRequireUtf8(data, position, registers);
/*      */                 } else {
/* 4049 */                   position = ArrayDecoders.decodeString(data, position, registers);
/*      */                 } 
/* 4051 */                 unsafe.putObject(message, fieldOffset, registers.object1);
/* 4052 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 9:
/* 4057 */               if (wireType == 2) {
/* 4058 */                 Object current = mutableMessageFieldForMerge(message, pos);
/*      */                 
/* 4060 */                 position = ArrayDecoders.mergeMessageField(current, 
/* 4061 */                     getMessageFieldSchema(pos), data, position, limit, registers);
/* 4062 */                 storeMessageField(message, pos, current);
/* 4063 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 10:
/* 4068 */               if (wireType == 2) {
/* 4069 */                 position = ArrayDecoders.decodeBytes(data, position, registers);
/* 4070 */                 unsafe.putObject(message, fieldOffset, registers.object1);
/* 4071 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 12:
/* 4076 */               if (wireType == 0) {
/* 4077 */                 position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 4078 */                 int enumValue = registers.int1;
/* 4079 */                 Internal.EnumVerifier enumVerifier = getEnumFieldVerifier(pos);
/* 4080 */                 if (!isLegacyEnumIsClosed(typeAndOffset) || enumVerifier == null || enumVerifier
/*      */                   
/* 4082 */                   .isInRange(enumValue)) {
/*      */                   
/* 4084 */                   unsafe.putInt(message, fieldOffset, enumValue);
/* 4085 */                   currentPresenceField |= presenceMask;
/*      */                   
/*      */                   continue;
/*      */                 } 
/* 4089 */                 getMutableUnknownFields(message).storeField(tag, Long.valueOf(enumValue));
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             
/*      */             case 15:
/* 4095 */               if (wireType == 0) {
/* 4096 */                 position = ArrayDecoders.decodeVarint32(data, position, registers);
/* 4097 */                 unsafe.putInt(message, fieldOffset, 
/* 4098 */                     CodedInputStream.decodeZigZag32(registers.int1));
/* 4099 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 16:
/* 4104 */               if (wireType == 0) {
/* 4105 */                 position = ArrayDecoders.decodeVarint64(data, position, registers);
/* 4106 */                 unsafe.putLong(message, fieldOffset, 
/* 4107 */                     CodedInputStream.decodeZigZag64(registers.long1));
/*      */                 
/* 4109 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */             case 17:
/* 4114 */               if (wireType == 3) {
/* 4115 */                 Object current = mutableMessageFieldForMerge(message, pos);
/* 4116 */                 int endTag = number << 3 | 0x4;
/*      */                 
/* 4118 */                 position = ArrayDecoders.mergeGroupField(current, 
/*      */                     
/* 4120 */                     getMessageFieldSchema(pos), data, position, limit, endTag, registers);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 4126 */                 storeMessageField(message, pos, current);
/* 4127 */                 currentPresenceField |= presenceMask;
/*      */                 continue;
/*      */               } 
/*      */               break;
/*      */           } 
/*      */ 
/*      */         
/* 4134 */         } else if (fieldType == 27) {
/*      */           
/* 4136 */           if (wireType == 2) {
/* 4137 */             Internal.ProtobufList<?> list = (Internal.ProtobufList)unsafe.getObject(message, fieldOffset);
/* 4138 */             if (!list.isModifiable()) {
/* 4139 */               int size = list.size();
/*      */               
/* 4141 */               list = list.mutableCopyWithCapacity(
/* 4142 */                   (size == 0) ? 10 : (size * 2));
/* 4143 */               unsafe.putObject(message, fieldOffset, list);
/*      */             } 
/*      */             
/* 4146 */             position = ArrayDecoders.decodeMessageList(
/* 4147 */                 getMessageFieldSchema(pos), tag, data, position, limit, list, registers);
/*      */             continue;
/*      */           } 
/* 4150 */         } else if (fieldType <= 49) {
/*      */           
/* 4152 */           int oldPosition = position;
/*      */           
/* 4154 */           position = parseRepeatedField(message, data, position, limit, tag, number, wireType, pos, typeAndOffset, fieldType, fieldOffset, registers);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4167 */           if (position != oldPosition) {
/*      */             continue;
/*      */           }
/* 4170 */         } else if (fieldType == 50) {
/* 4171 */           if (wireType == 2) {
/* 4172 */             int oldPosition = position;
/* 4173 */             position = parseMapField(message, data, position, limit, pos, fieldOffset, registers);
/* 4174 */             if (position != oldPosition) {
/*      */               continue;
/*      */             }
/*      */           } 
/*      */         } else {
/* 4179 */           int oldPosition = position;
/*      */           
/* 4181 */           position = parseOneofField(message, data, position, limit, tag, number, wireType, typeAndOffset, fieldType, fieldOffset, pos, registers);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4194 */           if (position != oldPosition) {
/*      */             continue;
/*      */           }
/*      */         } 
/*      */       } 
/* 4199 */       if (tag == endDelimited && endDelimited != 0) {
/*      */         break;
/*      */       }
/*      */       
/* 4203 */       if (this.hasExtensions && registers.extensionRegistry != 
/* 4204 */         ExtensionRegistryLite.getEmptyRegistry()) {
/*      */         
/* 4206 */         position = ArrayDecoders.decodeExtensionOrUnknownField(tag, data, position, limit, message, this.defaultInstance, (UnknownFieldSchema)this.unknownFieldSchema, registers);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4217 */       position = ArrayDecoders.decodeUnknownField(tag, data, position, limit, 
/* 4218 */           getMutableUnknownFields(message), registers);
/*      */     } 
/*      */     
/* 4221 */     if (currentPresenceFieldOffset != 1048575) {
/* 4222 */       unsafe.putInt(message, currentPresenceFieldOffset, currentPresenceField);
/*      */     }
/* 4224 */     UnknownFieldSetLite unknownFields = null;
/* 4225 */     for (int i = this.checkInitializedCount; i < this.repeatedFieldOffsetStart; i++)
/*      */     {
/* 4227 */       unknownFields = (UnknownFieldSetLite)filterMapUnknownEnumValues(message, this.intArray[i], unknownFields, this.unknownFieldSchema, message);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4234 */     if (unknownFields != null) {
/* 4235 */       this.unknownFieldSchema
/* 4236 */         .setBuilderToMessage(message, unknownFields);
/*      */     }
/* 4238 */     if (endDelimited == 0) {
/* 4239 */       if (position != limit) {
/* 4240 */         throw InvalidProtocolBufferException.parseFailure();
/*      */       }
/*      */     }
/* 4243 */     else if (position > limit || tag != endDelimited) {
/* 4244 */       throw InvalidProtocolBufferException.parseFailure();
/*      */     } 
/*      */     
/* 4247 */     return position;
/*      */   }
/*      */   
/*      */   private Object mutableMessageFieldForMerge(T message, int pos) {
/* 4251 */     Schema<Object> fieldSchema = getMessageFieldSchema(pos);
/* 4252 */     long offset = offset(typeAndOffsetAt(pos));
/*      */ 
/*      */     
/* 4255 */     if (!isFieldPresent(message, pos)) {
/* 4256 */       return fieldSchema.newInstance();
/*      */     }
/*      */ 
/*      */     
/* 4260 */     Object current = UNSAFE.getObject(message, offset);
/* 4261 */     if (isMutable(current)) {
/* 4262 */       return current;
/*      */     }
/*      */ 
/*      */     
/* 4266 */     Object newMessage = fieldSchema.newInstance();
/* 4267 */     if (current != null) {
/* 4268 */       fieldSchema.mergeFrom(newMessage, current);
/*      */     }
/* 4270 */     return newMessage;
/*      */   }
/*      */   
/*      */   private void storeMessageField(T message, int pos, Object field) {
/* 4274 */     UNSAFE.putObject(message, offset(typeAndOffsetAt(pos)), field);
/* 4275 */     setFieldPresent(message, pos);
/*      */   }
/*      */   
/*      */   private Object mutableOneofMessageFieldForMerge(T message, int fieldNumber, int pos) {
/* 4279 */     Schema<Object> fieldSchema = getMessageFieldSchema(pos);
/*      */ 
/*      */     
/* 4282 */     if (!isOneofPresent(message, fieldNumber, pos)) {
/* 4283 */       return fieldSchema.newInstance();
/*      */     }
/*      */ 
/*      */     
/* 4287 */     Object current = UNSAFE.getObject(message, offset(typeAndOffsetAt(pos)));
/* 4288 */     if (isMutable(current)) {
/* 4289 */       return current;
/*      */     }
/*      */ 
/*      */     
/* 4293 */     Object newMessage = fieldSchema.newInstance();
/* 4294 */     if (current != null) {
/* 4295 */       fieldSchema.mergeFrom(newMessage, current);
/*      */     }
/* 4297 */     return newMessage;
/*      */   }
/*      */   
/*      */   private void storeOneofMessageField(T message, int fieldNumber, int pos, Object field) {
/* 4301 */     UNSAFE.putObject(message, offset(typeAndOffsetAt(pos)), field);
/* 4302 */     setOneofPresent(message, fieldNumber, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void mergeFrom(T message, byte[] data, int position, int limit, ArrayDecoders.Registers registers) throws IOException {
/* 4308 */     parseMessage(message, data, position, limit, 0, registers);
/*      */   }
/*      */ 
/*      */   
/*      */   public void makeImmutable(T message) {
/* 4313 */     if (!isMutable(message)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4319 */     if (message instanceof GeneratedMessageLite) {
/* 4320 */       GeneratedMessageLite<?, ?> generatedMessage = (GeneratedMessageLite<?, ?>)message;
/* 4321 */       generatedMessage.clearMemoizedSerializedSize();
/* 4322 */       generatedMessage.clearMemoizedHashCode();
/* 4323 */       generatedMessage.markImmutable();
/*      */     } 
/*      */     
/* 4326 */     int bufferLength = this.buffer.length;
/* 4327 */     for (int pos = 0; pos < bufferLength; pos += 3) {
/* 4328 */       Object mapField; int typeAndOffset = typeAndOffsetAt(pos);
/* 4329 */       long offset = offset(typeAndOffset);
/* 4330 */       switch (type(typeAndOffset)) {
/*      */         case 9:
/*      */         case 17:
/* 4333 */           if (isFieldPresent(message, pos)) {
/* 4334 */             getMessageFieldSchema(pos).makeImmutable(UNSAFE.getObject(message, offset));
/*      */           }
/*      */           break;
/*      */         case 60:
/*      */         case 68:
/* 4339 */           if (isOneofPresent(message, numberAt(pos), pos)) {
/* 4340 */             getMessageFieldSchema(pos).makeImmutable(UNSAFE.getObject(message, offset));
/*      */           }
/*      */           break;
/*      */         case 18:
/*      */         case 19:
/*      */         case 20:
/*      */         case 21:
/*      */         case 22:
/*      */         case 23:
/*      */         case 24:
/*      */         case 25:
/*      */         case 26:
/*      */         case 27:
/*      */         case 28:
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/* 4375 */           this.listFieldSchema.makeImmutableListAt(message, offset);
/*      */           break;
/*      */         
/*      */         case 50:
/* 4379 */           mapField = UNSAFE.getObject(message, offset);
/* 4380 */           if (mapField != null) {
/* 4381 */             UNSAFE.putObject(message, offset, this.mapFieldSchema.toImmutable(mapField));
/*      */           }
/*      */           break;
/*      */       } 
/*      */     
/*      */     } 
/* 4387 */     this.unknownFieldSchema.makeImmutable(message);
/* 4388 */     if (this.hasExtensions) {
/* 4389 */       this.extensionSchema.makeImmutable(message);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final <K, V> void mergeMap(Object message, int pos, Object mapDefaultEntry, ExtensionRegistryLite extensionRegistry, Reader reader) throws IOException {
/* 4401 */     long offset = offset(typeAndOffsetAt(pos));
/* 4402 */     Object mapField = UnsafeUtil.getObject(message, offset);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4407 */     if (mapField == null) {
/* 4408 */       mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
/* 4409 */       UnsafeUtil.putObject(message, offset, mapField);
/* 4410 */     } else if (this.mapFieldSchema.isImmutable(mapField)) {
/* 4411 */       Object oldMapField = mapField;
/* 4412 */       mapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
/* 4413 */       this.mapFieldSchema.mergeFrom(mapField, oldMapField);
/* 4414 */       UnsafeUtil.putObject(message, offset, mapField);
/*      */     } 
/* 4416 */     reader.readMap(this.mapFieldSchema
/* 4417 */         .forMutableMapData(mapField), this.mapFieldSchema
/* 4418 */         .forMapMetadata(mapDefaultEntry), extensionRegistry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <UT, UB> UB filterMapUnknownEnumValues(Object message, int pos, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema, Object containerMessage) {
/* 4428 */     int fieldNumber = numberAt(pos);
/* 4429 */     long offset = offset(typeAndOffsetAt(pos));
/* 4430 */     Object mapField = UnsafeUtil.getObject(message, offset);
/* 4431 */     if (mapField == null) {
/* 4432 */       return unknownFields;
/*      */     }
/* 4434 */     Internal.EnumVerifier enumVerifier = getEnumFieldVerifier(pos);
/* 4435 */     if (enumVerifier == null) {
/* 4436 */       return unknownFields;
/*      */     }
/* 4438 */     Map<?, ?> mapData = this.mapFieldSchema.forMutableMapData(mapField);
/*      */ 
/*      */     
/* 4441 */     unknownFields = filterUnknownEnumMap(pos, fieldNumber, mapData, enumVerifier, unknownFields, unknownFieldSchema, containerMessage);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4449 */     return unknownFields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <K, V, UT, UB> UB filterUnknownEnumMap(int pos, int number, Map<K, V> mapData, Internal.EnumVerifier enumVerifier, UB unknownFields, UnknownFieldSchema<UT, UB> unknownFieldSchema, Object containerMessage) {
/* 4462 */     MapEntryLite.Metadata<K, V> metadata = (MapEntryLite.Metadata)this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(pos));
/* 4463 */     for (Iterator<Map.Entry<K, V>> it = mapData.entrySet().iterator(); it.hasNext(); ) {
/* 4464 */       Map.Entry<K, V> entry = it.next();
/* 4465 */       if (!enumVerifier.isInRange(((Integer)entry.getValue()).intValue())) {
/* 4466 */         if (unknownFields == null) {
/* 4467 */           unknownFields = unknownFieldSchema.getBuilderFromMessage(containerMessage);
/*      */         }
/*      */         
/* 4470 */         int entrySize = MapEntryLite.computeSerializedSize(metadata, entry.getKey(), entry.getValue());
/* 4471 */         ByteString.CodedBuilder codedBuilder = ByteString.newCodedBuilder(entrySize);
/* 4472 */         CodedOutputStream codedOutput = codedBuilder.getCodedOutput();
/*      */         try {
/* 4474 */           MapEntryLite.writeTo(codedOutput, metadata, entry.getKey(), entry.getValue());
/* 4475 */         } catch (IOException e) {
/*      */           
/* 4477 */           throw new RuntimeException(e);
/*      */         } 
/* 4479 */         unknownFieldSchema.addLengthDelimited(unknownFields, number, codedBuilder.build());
/* 4480 */         it.remove();
/*      */       } 
/*      */     } 
/* 4483 */     return unknownFields;
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isInitialized(T message) {
/* 4488 */     int currentPresenceFieldOffset = 1048575;
/* 4489 */     int currentPresenceField = 0;
/* 4490 */     for (int i = 0; i < this.checkInitializedCount; i++) {
/* 4491 */       int pos = this.intArray[i];
/* 4492 */       int number = numberAt(pos);
/* 4493 */       int typeAndOffset = typeAndOffsetAt(pos);
/*      */       
/* 4495 */       int presenceMaskAndOffset = this.buffer[pos + 2];
/* 4496 */       int presenceFieldOffset = presenceMaskAndOffset & 0xFFFFF;
/* 4497 */       int presenceMask = 1 << presenceMaskAndOffset >>> 20;
/* 4498 */       if (presenceFieldOffset != currentPresenceFieldOffset) {
/* 4499 */         currentPresenceFieldOffset = presenceFieldOffset;
/* 4500 */         if (currentPresenceFieldOffset != 1048575) {
/* 4501 */           currentPresenceField = UNSAFE.getInt(message, presenceFieldOffset);
/*      */         }
/*      */       } 
/*      */       
/* 4505 */       if (isRequired(typeAndOffset) && 
/* 4506 */         !isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask))
/*      */       {
/* 4508 */         return false;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4515 */       switch (type(typeAndOffset)) {
/*      */         case 9:
/*      */         case 17:
/* 4518 */           if (isFieldPresent(message, pos, currentPresenceFieldOffset, currentPresenceField, presenceMask) && 
/*      */             
/* 4520 */             !isInitialized(message, typeAndOffset, getMessageFieldSchema(pos))) {
/* 4521 */             return false;
/*      */           }
/*      */           break;
/*      */         case 27:
/*      */         case 49:
/* 4526 */           if (!isListInitialized(message, typeAndOffset, pos)) {
/* 4527 */             return false;
/*      */           }
/*      */           break;
/*      */         case 60:
/*      */         case 68:
/* 4532 */           if (isOneofPresent(message, number, pos) && 
/* 4533 */             !isInitialized(message, typeAndOffset, getMessageFieldSchema(pos))) {
/* 4534 */             return false;
/*      */           }
/*      */           break;
/*      */         case 50:
/* 4538 */           if (!isMapInitialized(message, typeAndOffset, pos)) {
/* 4539 */             return false;
/*      */           }
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */     
/*      */     } 
/* 4547 */     if (this.hasExtensions && 
/* 4548 */       !this.extensionSchema.getExtensions(message).isInitialized()) {
/* 4549 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 4553 */     return true;
/*      */   }
/*      */   
/*      */   private static boolean isInitialized(Object message, int typeAndOffset, Schema<Object> schema) {
/* 4557 */     Object nested = UnsafeUtil.getObject(message, offset(typeAndOffset));
/* 4558 */     return schema.isInitialized(nested);
/*      */   }
/*      */ 
/*      */   
/*      */   private <N> boolean isListInitialized(Object message, int typeAndOffset, int pos) {
/* 4563 */     List<N> list = (List<N>)UnsafeUtil.getObject(message, offset(typeAndOffset));
/* 4564 */     if (list.isEmpty()) {
/* 4565 */       return true;
/*      */     }
/*      */     
/* 4568 */     Schema<N> schema = getMessageFieldSchema(pos);
/* 4569 */     for (int i = 0; i < list.size(); i++) {
/* 4570 */       N nested = list.get(i);
/* 4571 */       if (!schema.isInitialized(nested)) {
/* 4572 */         return false;
/*      */       }
/*      */     } 
/* 4575 */     return true;
/*      */   }
/*      */   
/*      */   private boolean isMapInitialized(T message, int typeAndOffset, int pos) {
/* 4579 */     Map<?, ?> map = this.mapFieldSchema.forMapData(UnsafeUtil.getObject(message, offset(typeAndOffset)));
/* 4580 */     if (map.isEmpty()) {
/* 4581 */       return true;
/*      */     }
/* 4583 */     Object mapDefaultEntry = getMapFieldDefaultEntry(pos);
/* 4584 */     MapEntryLite.Metadata<?, ?> metadata = this.mapFieldSchema.forMapMetadata(mapDefaultEntry);
/* 4585 */     if (metadata.valueType.getJavaType() != WireFormat.JavaType.MESSAGE) {
/* 4586 */       return true;
/*      */     }
/*      */     
/* 4589 */     Schema<?> schema = null;
/* 4590 */     for (Object nested : map.values()) {
/* 4591 */       if (schema == null) {
/* 4592 */         schema = Protobuf.getInstance().schemaFor(nested.getClass());
/*      */       }
/* 4594 */       if (!schema.isInitialized(nested)) {
/* 4595 */         return false;
/*      */       }
/*      */     } 
/* 4598 */     return true;
/*      */   }
/*      */   
/*      */   private void writeString(int fieldNumber, Object value, Writer writer) throws IOException {
/* 4602 */     if (value instanceof String) {
/* 4603 */       writer.writeString(fieldNumber, (String)value);
/*      */     } else {
/* 4605 */       writer.writeBytes(fieldNumber, (ByteString)value);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readString(Object message, int typeAndOffset, Reader reader) throws IOException {
/* 4610 */     if (isEnforceUtf8(typeAndOffset)) {
/*      */       
/* 4612 */       UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readStringRequireUtf8());
/* 4613 */     } else if (this.lite) {
/*      */ 
/*      */       
/* 4616 */       UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readString());
/*      */     }
/*      */     else {
/*      */       
/* 4620 */       UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readBytes());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readStringList(Object message, int typeAndOffset, Reader reader) throws IOException {
/* 4625 */     if (isEnforceUtf8(typeAndOffset)) {
/* 4626 */       reader.readStringListRequireUtf8(this.listFieldSchema
/* 4627 */           .mutableListAt(message, offset(typeAndOffset)));
/*      */     } else {
/* 4629 */       reader.readStringList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <E> void readMessageList(Object message, int typeAndOffset, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 4640 */     long offset = offset(typeAndOffset);
/* 4641 */     reader.readMessageList(this.listFieldSchema
/* 4642 */         .mutableListAt(message, offset), schema, extensionRegistry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <E> void readGroupList(Object message, long offset, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 4652 */     reader.readGroupList(this.listFieldSchema
/* 4653 */         .mutableListAt(message, offset), schema, extensionRegistry);
/*      */   }
/*      */   
/*      */   private int numberAt(int pos) {
/* 4657 */     return this.buffer[pos];
/*      */   }
/*      */   
/*      */   private int typeAndOffsetAt(int pos) {
/* 4661 */     return this.buffer[pos + 1];
/*      */   }
/*      */   
/*      */   private int presenceMaskAndOffsetAt(int pos) {
/* 4665 */     return this.buffer[pos + 2];
/*      */   }
/*      */   
/*      */   private static int type(int value) {
/* 4669 */     return (value & 0xFF00000) >>> 20;
/*      */   }
/*      */   
/*      */   private static boolean isRequired(int value) {
/* 4673 */     return ((value & 0x10000000) != 0);
/*      */   }
/*      */   
/*      */   private static boolean isEnforceUtf8(int value) {
/* 4677 */     return ((value & 0x20000000) != 0);
/*      */   }
/*      */   
/*      */   private static boolean isLegacyEnumIsClosed(int value) {
/* 4681 */     return ((value & Integer.MIN_VALUE) != 0);
/*      */   }
/*      */   
/*      */   private static long offset(int value) {
/* 4685 */     return (value & 0xFFFFF);
/*      */   }
/*      */   
/*      */   private static boolean isMutable(Object message) {
/* 4689 */     if (message == null) {
/* 4690 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4695 */     if (message instanceof GeneratedMessageLite) {
/* 4696 */       return ((GeneratedMessageLite)message).isMutable();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4701 */     return true;
/*      */   }
/*      */   
/*      */   private static void checkMutable(Object message) {
/* 4705 */     if (!isMutable(message)) {
/* 4706 */       throw new IllegalArgumentException("Mutating immutable message: " + message);
/*      */     }
/*      */   }
/*      */   
/*      */   private static <T> double doubleAt(T message, long offset) {
/* 4711 */     return UnsafeUtil.getDouble(message, offset);
/*      */   }
/*      */   
/*      */   private static <T> float floatAt(T message, long offset) {
/* 4715 */     return UnsafeUtil.getFloat(message, offset);
/*      */   }
/*      */   
/*      */   private static <T> int intAt(T message, long offset) {
/* 4719 */     return UnsafeUtil.getInt(message, offset);
/*      */   }
/*      */   
/*      */   private static <T> long longAt(T message, long offset) {
/* 4723 */     return UnsafeUtil.getLong(message, offset);
/*      */   }
/*      */   
/*      */   private static <T> boolean booleanAt(T message, long offset) {
/* 4727 */     return UnsafeUtil.getBoolean(message, offset);
/*      */   }
/*      */   
/*      */   private static <T> double oneofDoubleAt(T message, long offset) {
/* 4731 */     return ((Double)UnsafeUtil.getObject(message, offset)).doubleValue();
/*      */   }
/*      */   
/*      */   private static <T> float oneofFloatAt(T message, long offset) {
/* 4735 */     return ((Float)UnsafeUtil.getObject(message, offset)).floatValue();
/*      */   }
/*      */   
/*      */   private static <T> int oneofIntAt(T message, long offset) {
/* 4739 */     return ((Integer)UnsafeUtil.getObject(message, offset)).intValue();
/*      */   }
/*      */   
/*      */   private static <T> long oneofLongAt(T message, long offset) {
/* 4743 */     return ((Long)UnsafeUtil.getObject(message, offset)).longValue();
/*      */   }
/*      */   
/*      */   private static <T> boolean oneofBooleanAt(T message, long offset) {
/* 4747 */     return ((Boolean)UnsafeUtil.getObject(message, offset)).booleanValue();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean arePresentForEquals(T message, T other, int pos) {
/* 4752 */     return (isFieldPresent(message, pos) == isFieldPresent(other, pos));
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isFieldPresent(T message, int pos, int presenceFieldOffset, int presenceField, int presenceMask) {
/* 4757 */     if (presenceFieldOffset == 1048575) {
/* 4758 */       return isFieldPresent(message, pos);
/*      */     }
/* 4760 */     return ((presenceField & presenceMask) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isFieldPresent(T message, int pos) {
/* 4765 */     int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
/* 4766 */     long presenceFieldOffset = (presenceMaskAndOffset & 0xFFFFF);
/* 4767 */     if (presenceFieldOffset == 1048575L) {
/* 4768 */       Object value; int typeAndOffset = typeAndOffsetAt(pos);
/* 4769 */       long offset = offset(typeAndOffset);
/* 4770 */       switch (type(typeAndOffset)) {
/*      */         case 0:
/* 4772 */           return (Double.doubleToRawLongBits(UnsafeUtil.getDouble(message, offset)) != 0L);
/*      */         case 1:
/* 4774 */           return (Float.floatToRawIntBits(UnsafeUtil.getFloat(message, offset)) != 0);
/*      */         case 2:
/* 4776 */           return (UnsafeUtil.getLong(message, offset) != 0L);
/*      */         case 3:
/* 4778 */           return (UnsafeUtil.getLong(message, offset) != 0L);
/*      */         case 4:
/* 4780 */           return (UnsafeUtil.getInt(message, offset) != 0);
/*      */         case 5:
/* 4782 */           return (UnsafeUtil.getLong(message, offset) != 0L);
/*      */         case 6:
/* 4784 */           return (UnsafeUtil.getInt(message, offset) != 0);
/*      */         case 7:
/* 4786 */           return UnsafeUtil.getBoolean(message, offset);
/*      */         case 8:
/* 4788 */           value = UnsafeUtil.getObject(message, offset);
/* 4789 */           if (value instanceof String)
/* 4790 */             return !((String)value).isEmpty(); 
/* 4791 */           if (value instanceof ByteString) {
/* 4792 */             return !ByteString.EMPTY.equals(value);
/*      */           }
/* 4794 */           throw new IllegalArgumentException();
/*      */         
/*      */         case 9:
/* 4797 */           return (UnsafeUtil.getObject(message, offset) != null);
/*      */         case 10:
/* 4799 */           return !ByteString.EMPTY.equals(UnsafeUtil.getObject(message, offset));
/*      */         case 11:
/* 4801 */           return (UnsafeUtil.getInt(message, offset) != 0);
/*      */         case 12:
/* 4803 */           return (UnsafeUtil.getInt(message, offset) != 0);
/*      */         case 13:
/* 4805 */           return (UnsafeUtil.getInt(message, offset) != 0);
/*      */         case 14:
/* 4807 */           return (UnsafeUtil.getLong(message, offset) != 0L);
/*      */         case 15:
/* 4809 */           return (UnsafeUtil.getInt(message, offset) != 0);
/*      */         case 16:
/* 4811 */           return (UnsafeUtil.getLong(message, offset) != 0L);
/*      */         case 17:
/* 4813 */           return (UnsafeUtil.getObject(message, offset) != null);
/*      */       } 
/* 4815 */       throw new IllegalArgumentException();
/*      */     } 
/*      */     
/* 4818 */     int presenceMask = 1 << presenceMaskAndOffset >>> 20;
/* 4819 */     return ((UnsafeUtil.getInt(message, (presenceMaskAndOffset & 0xFFFFF)) & presenceMask) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setFieldPresent(T message, int pos) {
/* 4824 */     int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
/* 4825 */     long presenceFieldOffset = (presenceMaskAndOffset & 0xFFFFF);
/* 4826 */     if (presenceFieldOffset == 1048575L) {
/*      */       return;
/*      */     }
/* 4829 */     int presenceMask = 1 << presenceMaskAndOffset >>> 20;
/* 4830 */     UnsafeUtil.putInt(message, presenceFieldOffset, 
/*      */ 
/*      */         
/* 4833 */         UnsafeUtil.getInt(message, presenceFieldOffset) | presenceMask);
/*      */   }
/*      */   
/*      */   private boolean isOneofPresent(T message, int fieldNumber, int pos) {
/* 4837 */     int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
/* 4838 */     return (UnsafeUtil.getInt(message, (presenceMaskAndOffset & 0xFFFFF)) == fieldNumber);
/*      */   }
/*      */   
/*      */   private boolean isOneofCaseEqual(T message, T other, int pos) {
/* 4842 */     int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
/* 4843 */     return 
/* 4844 */       (UnsafeUtil.getInt(message, (presenceMaskAndOffset & 0xFFFFF)) == UnsafeUtil.getInt(other, (presenceMaskAndOffset & 0xFFFFF)));
/*      */   }
/*      */   
/*      */   private void setOneofPresent(T message, int fieldNumber, int pos) {
/* 4848 */     int presenceMaskAndOffset = presenceMaskAndOffsetAt(pos);
/* 4849 */     UnsafeUtil.putInt(message, (presenceMaskAndOffset & 0xFFFFF), fieldNumber);
/*      */   }
/*      */   
/*      */   private int positionForFieldNumber(int number) {
/* 4853 */     if (number >= this.minFieldNumber && number <= this.maxFieldNumber) {
/* 4854 */       return slowPositionForFieldNumber(number, 0);
/*      */     }
/* 4856 */     return -1;
/*      */   }
/*      */   
/*      */   private int positionForFieldNumber(int number, int min) {
/* 4860 */     if (number >= this.minFieldNumber && number <= this.maxFieldNumber) {
/* 4861 */       return slowPositionForFieldNumber(number, min);
/*      */     }
/* 4863 */     return -1;
/*      */   }
/*      */   
/*      */   private int slowPositionForFieldNumber(int number, int min) {
/* 4867 */     int max = this.buffer.length / 3 - 1;
/* 4868 */     while (min <= max) {
/*      */       
/* 4870 */       int mid = max + min >>> 1;
/* 4871 */       int pos = mid * 3;
/* 4872 */       int midFieldNumber = numberAt(pos);
/* 4873 */       if (number == midFieldNumber)
/*      */       {
/* 4875 */         return pos;
/*      */       }
/* 4877 */       if (number < midFieldNumber) {
/*      */         
/* 4879 */         max = mid - 1;
/*      */         continue;
/*      */       } 
/* 4882 */       min = mid + 1;
/*      */     } 
/*      */     
/* 4885 */     return -1;
/*      */   }
/*      */   
/*      */   int getSchemaSize() {
/* 4889 */     return this.buffer.length * 3;
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MessageSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Stack;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DescriptorMessageInfoFactory
/*     */   implements MessageInfoFactory
/*     */ {
/*     */   private static final String GET_DEFAULT_INSTANCE_METHOD_NAME = "getDefaultInstance";
/*  40 */   private static final DescriptorMessageInfoFactory instance = new DescriptorMessageInfoFactory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   private static final Set<String> specialFieldNames = new HashSet<>(
/*     */       
/*  51 */       Arrays.asList(new String[] { "Class", "DefaultInstanceForType", "ParserForType", "SerializedSize", "AllFields", "DescriptorForType", "InitializationErrorString", "UnknownFields", "CachedSize" }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DescriptorMessageInfoFactory getInstance() {
/*  71 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSupported(Class<?> messageType) {
/*  76 */     return GeneratedMessage.class.isAssignableFrom(messageType);
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageInfo messageInfoFor(Class<?> messageType) {
/*  81 */     if (!GeneratedMessage.class.isAssignableFrom(messageType)) {
/*  82 */       throw new IllegalArgumentException("Unsupported message type: " + messageType.getName());
/*     */     }
/*     */     
/*  85 */     return convert(messageType, descriptorForType(messageType));
/*     */   }
/*     */   
/*     */   private static Message getDefaultInstance(Class<?> messageType) {
/*     */     try {
/*  90 */       Method method = messageType.getDeclaredMethod("getDefaultInstance", new Class[0]);
/*  91 */       return (Message)method.invoke(null, new Object[0]);
/*  92 */     } catch (Exception e) {
/*  93 */       throw new IllegalArgumentException("Unable to get default instance for message class " + messageType
/*  94 */           .getName(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Descriptors.Descriptor descriptorForType(Class<?> messageType) {
/*  99 */     return getDefaultInstance(messageType).getDescriptorForType();
/*     */   }
/*     */   
/*     */   private static ProtoSyntax convertSyntax(DescriptorProtos.Edition edition) {
/* 103 */     switch (edition) {
/*     */       case BOOL:
/* 105 */         return ProtoSyntax.PROTO2;
/*     */       case BYTES:
/* 107 */         return ProtoSyntax.PROTO3;
/*     */     } 
/* 109 */     return ProtoSyntax.EDITIONS;
/*     */   }
/*     */ 
/*     */   
/*     */   private static MessageInfo convert(Class<?> messageType, Descriptors.Descriptor messageDescriptor) {
/* 114 */     List<Descriptors.FieldDescriptor> fieldDescriptors = messageDescriptor.getFields();
/*     */     
/* 116 */     StructuralMessageInfo.Builder builder = StructuralMessageInfo.newBuilder(fieldDescriptors.size());
/* 117 */     builder.withDefaultInstance(getDefaultInstance(messageType));
/* 118 */     builder.withSyntax(convertSyntax(messageDescriptor.getFile().getEdition()));
/* 119 */     builder.withMessageSetWireFormat(messageDescriptor.getOptions().getMessageSetWireFormat());
/*     */     
/* 121 */     OneofState oneofState = new OneofState();
/*     */     
/* 123 */     int bitFieldIndex = 0;
/* 124 */     int presenceMask = 1;
/* 125 */     Field bitField = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     for (int i = 0; i < fieldDescriptors.size(); i++) {
/* 132 */       final Descriptors.FieldDescriptor fd = fieldDescriptors.get(i);
/* 133 */       boolean enforceUtf8 = fd.needsUtf8Check();
/* 134 */       Internal.EnumVerifier enumVerifier = null;
/*     */       
/* 136 */       if (fd.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM && fd
/* 137 */         .legacyEnumFieldTreatedAsClosed()) {
/* 138 */         enumVerifier = new Internal.EnumVerifier()
/*     */           {
/*     */             public boolean isInRange(int number)
/*     */             {
/* 142 */               return (fd.getEnumType().findValueByNumber(number) != null);
/*     */             }
/*     */           };
/*     */       }
/* 146 */       if (fd.getRealContainingOneof() != null) {
/*     */         
/* 148 */         builder.withField(buildOneofMember(messageType, fd, oneofState, enforceUtf8, enumVerifier));
/*     */       }
/*     */       else {
/*     */         
/* 152 */         Field field = field(messageType, fd);
/* 153 */         int number = fd.getNumber();
/* 154 */         FieldType type = getFieldType(fd);
/*     */ 
/*     */         
/* 157 */         if (!fd.hasPresence()) {
/*     */           FieldInfo fieldImplicitPresence;
/* 159 */           if (fd.isMapField()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 165 */             final Descriptors.FieldDescriptor valueField = fd.getMessageType().findFieldByNumber(2);
/* 166 */             if (valueField.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM && valueField
/* 167 */               .legacyEnumFieldTreatedAsClosed()) {
/* 168 */               enumVerifier = new Internal.EnumVerifier()
/*     */                 {
/*     */                   public boolean isInRange(int number)
/*     */                   {
/* 172 */                     return (valueField.getEnumType().findValueByNumber(number) != null);
/*     */                   }
/*     */                 };
/*     */             }
/*     */             
/* 177 */             fieldImplicitPresence = FieldInfo.forMapField(field, number, 
/*     */ 
/*     */                 
/* 180 */                 SchemaUtil.getMapDefaultEntry(messageType, fd.getName()), enumVerifier);
/*     */           }
/* 182 */           else if (fd.isRepeated() && fd.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/*     */             
/* 184 */             fieldImplicitPresence = FieldInfo.forRepeatedMessageField(field, number, type, 
/* 185 */                 getTypeForRepeatedMessageField(messageType, fd));
/* 186 */           } else if (fd.isPacked()) {
/* 187 */             if (enumVerifier != null) {
/*     */               
/* 189 */               fieldImplicitPresence = FieldInfo.forPackedFieldWithEnumVerifier(field, number, type, enumVerifier, 
/* 190 */                   cachedSizeField(messageType, fd));
/*     */             } else {
/*     */               
/* 193 */               fieldImplicitPresence = FieldInfo.forPackedField(field, number, type, cachedSizeField(messageType, fd));
/*     */             }
/*     */           
/* 196 */           } else if (enumVerifier != null) {
/* 197 */             fieldImplicitPresence = FieldInfo.forFieldWithEnumVerifier(field, number, type, enumVerifier);
/*     */           } else {
/* 199 */             fieldImplicitPresence = FieldInfo.forField(field, number, type, enforceUtf8);
/*     */           } 
/*     */           
/* 202 */           builder.withField(fieldImplicitPresence);
/*     */         } else {
/*     */           FieldInfo fieldExplicitPresence;
/*     */ 
/*     */ 
/*     */           
/* 208 */           if (bitField == null)
/*     */           {
/* 210 */             bitField = bitField(messageType, bitFieldIndex);
/*     */           }
/* 212 */           if (fd.isRequired()) {
/*     */             
/* 214 */             fieldExplicitPresence = FieldInfo.forLegacyRequiredField(field, number, type, bitField, presenceMask, enforceUtf8, enumVerifier);
/*     */           }
/*     */           else {
/*     */             
/* 218 */             fieldExplicitPresence = FieldInfo.forExplicitPresenceField(field, number, type, bitField, presenceMask, enforceUtf8, enumVerifier);
/*     */           } 
/*     */           
/* 221 */           builder.withField(fieldExplicitPresence);
/*     */ 
/*     */           
/* 224 */           presenceMask <<= 1;
/* 225 */           if (presenceMask == 0) {
/* 226 */             bitField = null;
/* 227 */             presenceMask = 1;
/* 228 */             bitFieldIndex++;
/*     */           } 
/*     */         } 
/*     */       } 
/* 232 */     }  List<Integer> fieldsToCheckIsInitialized = new ArrayList<>();
/* 233 */     for (int j = 0; j < fieldDescriptors.size(); j++) {
/* 234 */       final Descriptors.FieldDescriptor fd = fieldDescriptors.get(j);
/* 235 */       if (fd.isRequired() || (fd
/* 236 */         .getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE && 
/* 237 */         needsIsInitializedCheck(fd.getMessageType()))) {
/* 238 */         fieldsToCheckIsInitialized.add(Integer.valueOf(fd.getNumber()));
/*     */       }
/*     */     } 
/* 241 */     int[] numbers = new int[fieldsToCheckIsInitialized.size()];
/* 242 */     for (int k = 0; k < fieldsToCheckIsInitialized.size(); k++) {
/* 243 */       numbers[k] = ((Integer)fieldsToCheckIsInitialized.get(k)).intValue();
/*     */     }
/* 245 */     if (numbers.length > 0) {
/* 246 */       builder.withCheckInitialized(numbers);
/*     */     }
/* 248 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class IsInitializedCheckAnalyzer
/*     */   {
/*     */     private final Map<Descriptors.Descriptor, Boolean> resultCache;
/*     */ 
/*     */     
/*     */     private int index;
/*     */ 
/*     */     
/*     */     private final Stack<Node> stack;
/*     */ 
/*     */     
/*     */     private final Map<Descriptors.Descriptor, Node> nodeCache;
/*     */ 
/*     */ 
/*     */     
/*     */     IsInitializedCheckAnalyzer() {
/* 269 */       this.resultCache = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       this.index = 0;
/* 275 */       this.stack = new Stack<>();
/* 276 */       this.nodeCache = new HashMap<>();
/*     */     }
/*     */     public boolean needsIsInitializedCheck(Descriptors.Descriptor descriptor) {
/* 279 */       Boolean cachedValue = this.resultCache.get(descriptor);
/* 280 */       if (cachedValue != null) {
/* 281 */         return cachedValue.booleanValue();
/*     */       }
/* 283 */       synchronized (this) {
/*     */ 
/*     */         
/* 286 */         cachedValue = this.resultCache.get(descriptor);
/* 287 */         if (cachedValue != null) {
/* 288 */           return cachedValue.booleanValue();
/*     */         }
/* 290 */         return (dfs(descriptor)).component.needsIsInitializedCheck;
/*     */       } 
/*     */     }
/*     */     
/*     */     private static class Node {
/*     */       final Descriptors.Descriptor descriptor;
/*     */       final int index;
/*     */       int lowLink;
/*     */       DescriptorMessageInfoFactory.IsInitializedCheckAnalyzer.StronglyConnectedComponent component;
/*     */       
/*     */       Node(Descriptors.Descriptor descriptor, int index) {
/* 301 */         this.descriptor = descriptor;
/* 302 */         this.index = index;
/* 303 */         this.lowLink = index;
/* 304 */         this.component = null;
/*     */       } }
/*     */     private static class StronglyConnectedComponent { final List<Descriptors.Descriptor> messages;
/*     */       
/*     */       private StronglyConnectedComponent() {
/* 309 */         this.messages = new ArrayList<>();
/* 310 */         this.needsIsInitializedCheck = false;
/*     */       }
/*     */       boolean needsIsInitializedCheck; }
/*     */     private Node dfs(Descriptors.Descriptor descriptor) {
/* 314 */       Node result = new Node(descriptor, this.index++);
/* 315 */       this.stack.push(result);
/* 316 */       this.nodeCache.put(descriptor, result);
/*     */ 
/*     */       
/* 319 */       for (Descriptors.FieldDescriptor field : descriptor.getFields()) {
/* 320 */         if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 321 */           Node child = this.nodeCache.get(field.getMessageType());
/* 322 */           if (child == null) {
/*     */             
/* 324 */             child = dfs(field.getMessageType());
/* 325 */             result.lowLink = Math.min(result.lowLink, child.lowLink); continue;
/*     */           } 
/* 327 */           if (child.component == null)
/*     */           {
/* 329 */             result.lowLink = Math.min(result.lowLink, child.lowLink);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 335 */       if (result.index == result.lowLink) {
/*     */         Node node;
/* 337 */         StronglyConnectedComponent component = new StronglyConnectedComponent();
/*     */         do {
/* 339 */           node = this.stack.pop();
/* 340 */           node.component = component;
/* 341 */           component.messages.add(node.descriptor);
/* 342 */         } while (node != result);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 347 */         analyze(component);
/*     */       } 
/*     */       
/* 350 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     private void analyze(StronglyConnectedComponent component) {
/* 355 */       boolean needsIsInitializedCheck = false;
/*     */       
/* 357 */       label30: for (Descriptors.Descriptor descriptor : component.messages) {
/* 358 */         if (descriptor.isExtendable()) {
/* 359 */           needsIsInitializedCheck = true;
/*     */           
/*     */           break;
/*     */         } 
/* 363 */         for (Descriptors.FieldDescriptor field : descriptor.getFields()) {
/* 364 */           if (field.isRequired()) {
/* 365 */             needsIsInitializedCheck = true;
/*     */             
/*     */             break label30;
/*     */           } 
/* 369 */           if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/*     */ 
/*     */             
/* 372 */             Node node = this.nodeCache.get(field.getMessageType());
/* 373 */             if (node.component != component && 
/* 374 */               node.component.needsIsInitializedCheck) {
/* 375 */               needsIsInitializedCheck = true;
/*     */               
/*     */               break label30;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 383 */       component.needsIsInitializedCheck = needsIsInitializedCheck;
/*     */       
/* 385 */       for (Descriptors.Descriptor descriptor : component.messages) {
/* 386 */         this.resultCache.put(descriptor, Boolean.valueOf(component.needsIsInitializedCheck));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 391 */   private static IsInitializedCheckAnalyzer isInitializedCheckAnalyzer = new IsInitializedCheckAnalyzer();
/*     */ 
/*     */   
/*     */   private static boolean needsIsInitializedCheck(Descriptors.Descriptor descriptor) {
/* 395 */     return isInitializedCheckAnalyzer.needsIsInitializedCheck(descriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FieldInfo buildOneofMember(Class<?> messageType, Descriptors.FieldDescriptor fd, OneofState oneofState, boolean enforceUtf8, Internal.EnumVerifier enumVerifier) {
/* 405 */     OneofInfo oneof = oneofState.getOneof(messageType, fd.getContainingOneof());
/* 406 */     FieldType type = getFieldType(fd);
/* 407 */     Class<?> oneofStoredType = getOneofStoredType(messageType, fd, type);
/* 408 */     return FieldInfo.forOneofMemberField(fd
/* 409 */         .getNumber(), type, oneof, oneofStoredType, enforceUtf8, enumVerifier);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class<?> getOneofStoredType(Class<?> messageType, Descriptors.FieldDescriptor fd, FieldType type) {
/* 414 */     switch (type.getJavaType()) {
/*     */       case BOOL:
/* 416 */         return Boolean.class;
/*     */       case BYTES:
/* 418 */         return ByteString.class;
/*     */       case DOUBLE:
/* 420 */         return Double.class;
/*     */       case ENUM:
/* 422 */         return Float.class;
/*     */       case FIXED32:
/*     */       case FIXED64:
/* 425 */         return Integer.class;
/*     */       case FLOAT:
/* 427 */         return Long.class;
/*     */       case GROUP:
/* 429 */         return String.class;
/*     */       case INT32:
/* 431 */         return getOneofStoredTypeForMessage(messageType, fd);
/*     */     } 
/* 433 */     throw new IllegalArgumentException("Invalid type for oneof: " + type);
/*     */   }
/*     */ 
/*     */   
/*     */   private static FieldType getFieldType(Descriptors.FieldDescriptor fd) {
/* 438 */     switch (fd.getType()) {
/*     */       case BOOL:
/* 440 */         if (!fd.isRepeated()) {
/* 441 */           return FieldType.BOOL;
/*     */         }
/* 443 */         return fd.isPacked() ? FieldType.BOOL_LIST_PACKED : FieldType.BOOL_LIST;
/*     */       case BYTES:
/* 445 */         return fd.isRepeated() ? FieldType.BYTES_LIST : FieldType.BYTES;
/*     */       case DOUBLE:
/* 447 */         if (!fd.isRepeated()) {
/* 448 */           return FieldType.DOUBLE;
/*     */         }
/* 450 */         return fd.isPacked() ? FieldType.DOUBLE_LIST_PACKED : FieldType.DOUBLE_LIST;
/*     */       case ENUM:
/* 452 */         if (!fd.isRepeated()) {
/* 453 */           return FieldType.ENUM;
/*     */         }
/* 455 */         return fd.isPacked() ? FieldType.ENUM_LIST_PACKED : FieldType.ENUM_LIST;
/*     */       case FIXED32:
/* 457 */         if (!fd.isRepeated()) {
/* 458 */           return FieldType.FIXED32;
/*     */         }
/* 460 */         return fd.isPacked() ? FieldType.FIXED32_LIST_PACKED : FieldType.FIXED32_LIST;
/*     */       case FIXED64:
/* 462 */         if (!fd.isRepeated()) {
/* 463 */           return FieldType.FIXED64;
/*     */         }
/* 465 */         return fd.isPacked() ? FieldType.FIXED64_LIST_PACKED : FieldType.FIXED64_LIST;
/*     */       case FLOAT:
/* 467 */         if (!fd.isRepeated()) {
/* 468 */           return FieldType.FLOAT;
/*     */         }
/* 470 */         return fd.isPacked() ? FieldType.FLOAT_LIST_PACKED : FieldType.FLOAT_LIST;
/*     */       case GROUP:
/* 472 */         return fd.isRepeated() ? FieldType.GROUP_LIST : FieldType.GROUP;
/*     */       case INT32:
/* 474 */         if (!fd.isRepeated()) {
/* 475 */           return FieldType.INT32;
/*     */         }
/* 477 */         return fd.isPacked() ? FieldType.INT32_LIST_PACKED : FieldType.INT32_LIST;
/*     */       case INT64:
/* 479 */         if (!fd.isRepeated()) {
/* 480 */           return FieldType.INT64;
/*     */         }
/* 482 */         return fd.isPacked() ? FieldType.INT64_LIST_PACKED : FieldType.INT64_LIST;
/*     */       case MESSAGE:
/* 484 */         if (fd.isMapField()) {
/* 485 */           return FieldType.MAP;
/*     */         }
/* 487 */         return fd.isRepeated() ? FieldType.MESSAGE_LIST : FieldType.MESSAGE;
/*     */       case SFIXED32:
/* 489 */         if (!fd.isRepeated()) {
/* 490 */           return FieldType.SFIXED32;
/*     */         }
/* 492 */         return fd.isPacked() ? FieldType.SFIXED32_LIST_PACKED : FieldType.SFIXED32_LIST;
/*     */       case SFIXED64:
/* 494 */         if (!fd.isRepeated()) {
/* 495 */           return FieldType.SFIXED64;
/*     */         }
/* 497 */         return fd.isPacked() ? FieldType.SFIXED64_LIST_PACKED : FieldType.SFIXED64_LIST;
/*     */       case SINT32:
/* 499 */         if (!fd.isRepeated()) {
/* 500 */           return FieldType.SINT32;
/*     */         }
/* 502 */         return fd.isPacked() ? FieldType.SINT32_LIST_PACKED : FieldType.SINT32_LIST;
/*     */       case SINT64:
/* 504 */         if (!fd.isRepeated()) {
/* 505 */           return FieldType.SINT64;
/*     */         }
/* 507 */         return fd.isPacked() ? FieldType.SINT64_LIST_PACKED : FieldType.SINT64_LIST;
/*     */       case STRING:
/* 509 */         return fd.isRepeated() ? FieldType.STRING_LIST : FieldType.STRING;
/*     */       case UINT32:
/* 511 */         if (!fd.isRepeated()) {
/* 512 */           return FieldType.UINT32;
/*     */         }
/* 514 */         return fd.isPacked() ? FieldType.UINT32_LIST_PACKED : FieldType.UINT32_LIST;
/*     */       case UINT64:
/* 516 */         if (!fd.isRepeated()) {
/* 517 */           return FieldType.UINT64;
/*     */         }
/* 519 */         return fd.isPacked() ? FieldType.UINT64_LIST_PACKED : FieldType.UINT64_LIST;
/*     */     } 
/* 521 */     throw new IllegalArgumentException("Unsupported field type: " + fd.getType());
/*     */   }
/*     */ 
/*     */   
/*     */   private static Field bitField(Class<?> messageType, int index) {
/* 526 */     return field(messageType, "bitField" + index + "_");
/*     */   }
/*     */   
/*     */   private static Field field(Class<?> messageType, Descriptors.FieldDescriptor fd) {
/* 530 */     return field(messageType, getFieldName(fd));
/*     */   }
/*     */   
/*     */   private static Field cachedSizeField(Class<?> messageType, Descriptors.FieldDescriptor fd) {
/* 534 */     return field(messageType, getCachedSizeFieldName(fd));
/*     */   }
/*     */   
/*     */   private static Field field(Class<?> messageType, String fieldName) {
/*     */     try {
/* 539 */       return messageType.getDeclaredField(fieldName);
/* 540 */     } catch (Exception e) {
/* 541 */       throw new IllegalArgumentException("Unable to find field " + fieldName + " in message class " + messageType
/* 542 */           .getName(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String getFieldName(Descriptors.FieldDescriptor fd) {
/* 549 */     String suffix, name = (fd.getType() == Descriptors.FieldDescriptor.Type.GROUP) ? fd.getMessageType().getName() : fd.getName();
/*     */ 
/*     */ 
/*     */     
/* 553 */     String upperCamelCaseName = snakeCaseToUpperCamelCase(name);
/*     */ 
/*     */ 
/*     */     
/* 557 */     if (specialFieldNames.contains(upperCamelCaseName)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 566 */       suffix = "__";
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 575 */       suffix = "_";
/*     */     } 
/* 577 */     return snakeCaseToLowerCamelCase(name) + suffix;
/*     */   }
/*     */   
/*     */   private static String getCachedSizeFieldName(Descriptors.FieldDescriptor fd) {
/* 581 */     return snakeCaseToLowerCamelCase(fd.getName()) + "MemoizedSerializedSize";
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static String snakeCaseToLowerCamelCase(String snakeCase) {
/* 598 */     return snakeCaseToCamelCase(snakeCase, false);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static String snakeCaseToUpperCamelCase(String snakeCase) {
/* 615 */     return snakeCaseToCamelCase(snakeCase, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String snakeCaseToCamelCase(String snakeCase, boolean capFirst) {
/* 646 */     StringBuilder sb = new StringBuilder(snakeCase.length() + 1);
/* 647 */     boolean capNext = capFirst;
/* 648 */     for (int ctr = 0; ctr < snakeCase.length(); ctr++) {
/* 649 */       char next = snakeCase.charAt(ctr);
/* 650 */       if (next == '_') {
/* 651 */         capNext = true;
/* 652 */       } else if (Character.isDigit(next)) {
/* 653 */         sb.append(next);
/* 654 */         capNext = true;
/* 655 */       } else if (capNext) {
/* 656 */         sb.append(Character.toUpperCase(next));
/* 657 */         capNext = false;
/* 658 */       } else if (ctr == 0) {
/* 659 */         sb.append(Character.toLowerCase(next));
/*     */       } else {
/* 661 */         sb.append(next);
/*     */       } 
/*     */     } 
/* 664 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getOneofStoredTypeForMessage(Class<?> messageType, Descriptors.FieldDescriptor fd) {
/*     */     try {
/* 672 */       String name = (fd.getType() == Descriptors.FieldDescriptor.Type.GROUP) ? fd.getMessageType().getName() : fd.getName();
/* 673 */       Method getter = messageType.getDeclaredMethod(getterForField(name), new Class[0]);
/* 674 */       return getter.getReturnType();
/* 675 */     } catch (Exception e) {
/* 676 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class<?> getTypeForRepeatedMessageField(Class<?> messageType, Descriptors.FieldDescriptor fd) {
/*     */     try {
/* 683 */       String name = (fd.getType() == Descriptors.FieldDescriptor.Type.GROUP) ? fd.getMessageType().getName() : fd.getName();
/* 684 */       Method getter = messageType.getDeclaredMethod(getterForField(name), new Class[] { int.class });
/* 685 */       return getter.getReturnType();
/* 686 */     } catch (Exception e) {
/* 687 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getterForField(String snakeCase) {
/* 693 */     String camelCase = snakeCaseToLowerCamelCase(snakeCase);
/* 694 */     StringBuilder builder = new StringBuilder("get");
/*     */     
/* 696 */     builder.append(Character.toUpperCase(camelCase.charAt(0)));
/* 697 */     builder.append(camelCase.substring(1, camelCase.length()));
/* 698 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private static final class OneofState {
/* 702 */     private OneofInfo[] oneofs = new OneofInfo[2];
/*     */     
/*     */     OneofInfo getOneof(Class<?> messageType, Descriptors.OneofDescriptor desc) {
/* 705 */       int index = desc.getIndex();
/* 706 */       if (index >= this.oneofs.length)
/*     */       {
/* 708 */         this.oneofs = Arrays.<OneofInfo>copyOf(this.oneofs, index * 2);
/*     */       }
/* 710 */       OneofInfo info = this.oneofs[index];
/* 711 */       if (info == null) {
/* 712 */         info = newInfo(messageType, desc);
/* 713 */         this.oneofs[index] = info;
/*     */       } 
/* 715 */       return info;
/*     */     }
/*     */     
/*     */     private static OneofInfo newInfo(Class<?> messageType, Descriptors.OneofDescriptor desc) {
/* 719 */       String camelCase = DescriptorMessageInfoFactory.snakeCaseToLowerCamelCase(desc.getName());
/* 720 */       String valueFieldName = camelCase + "_";
/* 721 */       String caseFieldName = camelCase + "Case_";
/*     */       
/* 723 */       return new OneofInfo(desc
/* 724 */           .getIndex(), DescriptorMessageInfoFactory.field(messageType, caseFieldName), DescriptorMessageInfoFactory.field(messageType, valueFieldName));
/*     */     }
/*     */     
/*     */     private OneofState() {}
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\DescriptorMessageInfoFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
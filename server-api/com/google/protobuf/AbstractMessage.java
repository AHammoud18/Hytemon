/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMessage
/*     */   extends AbstractMessageLite
/*     */   implements Message
/*     */ {
/*     */   public boolean isInitialized() {
/*  42 */     return MessageReflection.isInitialized(this);
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
/*     */   protected Message.Builder newBuilderForType(BuilderParent parent) {
/*  67 */     throw new UnsupportedOperationException("Nested builder is not supported for this type.");
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> findInitializationErrors() {
/*  72 */     return MessageReflection.findMissingFields(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInitializationErrorString() {
/*  77 */     return MessageReflection.delimitWithCommas(findInitializationErrors());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
/*  83 */     throw new UnsupportedOperationException("hasOneof() is not implemented.");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
/*  89 */     throw new UnsupportedOperationException("getOneofFieldDescriptor() is not implemented.");
/*     */   }
/*     */ 
/*     */   
/*     */   public final String toString() {
/*  94 */     return TextFormat.Printer.getOutputModePrinter()
/*  95 */       .printToString(this, TextFormat.Printer.FieldReporterLevel.ABSTRACT_TO_STRING);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(CodedOutputStream output) throws IOException {
/* 100 */     MessageReflection.writeMessageTo(this, getAllFields(), output, false);
/*     */   }
/*     */   
/* 103 */   protected int memoizedSize = -1;
/*     */ 
/*     */   
/*     */   int getMemoizedSerializedSize() {
/* 107 */     return this.memoizedSize;
/*     */   }
/*     */ 
/*     */   
/*     */   void setMemoizedSerializedSize(int size) {
/* 112 */     this.memoizedSize = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 117 */     int size = this.memoizedSize;
/* 118 */     if (size != -1) {
/* 119 */       return size;
/*     */     }
/*     */     
/* 122 */     this.memoizedSize = MessageReflection.getSerializedSize(this, getAllFields());
/* 123 */     return this.memoizedSize;
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
/*     */   public boolean equals(Object other) {
/* 139 */     if (other == this) {
/* 140 */       return true;
/*     */     }
/* 142 */     if (!(other instanceof Message)) {
/* 143 */       return false;
/*     */     }
/* 145 */     Message otherMessage = (Message)other;
/* 146 */     if (getDescriptorForType() != otherMessage.getDescriptorForType()) {
/* 147 */       return false;
/*     */     }
/* 149 */     return (compareFields(getAllFields(), otherMessage.getAllFields()) && 
/* 150 */       getUnknownFields().equals(otherMessage.getUnknownFields()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 155 */     int hash = this.memoizedHashCode;
/* 156 */     if (hash == 0) {
/* 157 */       hash = 41;
/* 158 */       hash = 19 * hash + getDescriptorForType().hashCode();
/* 159 */       hash = hashFields(hash, getAllFields());
/* 160 */       hash = 29 * hash + getUnknownFields().hashCode();
/* 161 */       this.memoizedHashCode = hash;
/*     */     } 
/* 163 */     return hash;
/*     */   }
/*     */   
/*     */   private static ByteString toByteString(Object value) {
/* 167 */     if (value instanceof byte[]) {
/* 168 */       return ByteString.copyFrom((byte[])value);
/*     */     }
/* 170 */     return (ByteString)value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean compareBytes(Object a, Object b) {
/* 179 */     if (a instanceof byte[] && b instanceof byte[]) {
/* 180 */       return Arrays.equals((byte[])a, (byte[])b);
/*     */     }
/* 182 */     return toByteString(a).equals(toByteString(b));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map convertMapEntryListToMap(List list) {
/* 188 */     if (list.isEmpty()) {
/* 189 */       return Collections.emptyMap();
/*     */     }
/* 191 */     Map<Object, Object> result = new HashMap<>();
/* 192 */     Iterator<Message> iterator = list.iterator();
/* 193 */     Message entry = iterator.next();
/* 194 */     Descriptors.Descriptor descriptor = entry.getDescriptorForType();
/* 195 */     Descriptors.FieldDescriptor key = descriptor.findFieldByName("key");
/* 196 */     Descriptors.FieldDescriptor value = descriptor.findFieldByName("value");
/* 197 */     Object fieldValue = entry.getField(value);
/* 198 */     if (fieldValue instanceof Descriptors.EnumValueDescriptor) {
/* 199 */       fieldValue = Integer.valueOf(((Descriptors.EnumValueDescriptor)fieldValue).getNumber());
/*     */     }
/* 201 */     result.put(entry.getField(key), fieldValue);
/* 202 */     while (iterator.hasNext()) {
/* 203 */       entry = iterator.next();
/* 204 */       fieldValue = entry.getField(value);
/* 205 */       if (fieldValue instanceof Descriptors.EnumValueDescriptor) {
/* 206 */         fieldValue = Integer.valueOf(((Descriptors.EnumValueDescriptor)fieldValue).getNumber());
/*     */       }
/* 208 */       result.put(entry.getField(key), fieldValue);
/*     */     } 
/* 210 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean compareMapField(Object a, Object b) {
/* 216 */     Map<?, ?> ma = convertMapEntryListToMap((List)a);
/* 217 */     Map<?, ?> mb = convertMapEntryListToMap((List)b);
/* 218 */     return MapFieldLite.equals(ma, mb);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean compareFields(Map<Descriptors.FieldDescriptor, Object> a, Map<Descriptors.FieldDescriptor, Object> b) {
/* 229 */     if (a.size() != b.size()) {
/* 230 */       return false;
/*     */     }
/* 232 */     for (Descriptors.FieldDescriptor descriptor : a.keySet()) {
/* 233 */       if (!b.containsKey(descriptor)) {
/* 234 */         return false;
/*     */       }
/* 236 */       Object value1 = a.get(descriptor);
/* 237 */       Object value2 = b.get(descriptor);
/* 238 */       if (descriptor.getType() == Descriptors.FieldDescriptor.Type.BYTES) {
/* 239 */         if (descriptor.isRepeated()) {
/* 240 */           List<?> list1 = (List)value1;
/* 241 */           List<?> list2 = (List)value2;
/* 242 */           if (list1.size() != list2.size()) {
/* 243 */             return false;
/*     */           }
/* 245 */           for (int i = 0; i < list1.size(); i++) {
/* 246 */             if (!compareBytes(list1.get(i), list2.get(i))) {
/* 247 */               return false;
/*     */             }
/*     */           } 
/*     */           continue;
/*     */         } 
/* 252 */         if (!compareBytes(value1, value2))
/* 253 */           return false; 
/*     */         continue;
/*     */       } 
/* 256 */       if (descriptor.isMapField()) {
/* 257 */         if (!compareMapField(value1, value2)) {
/* 258 */           return false;
/*     */         }
/*     */         continue;
/*     */       } 
/* 262 */       if (!value1.equals(value2)) {
/* 263 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 267 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hashMapField(Object value) {
/* 273 */     return MapFieldLite.calculateHashCodeForMap(convertMapEntryListToMap((List)value));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int hashFields(int hash, Map<Descriptors.FieldDescriptor, Object> map) {
/* 279 */     for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : map.entrySet()) {
/* 280 */       Descriptors.FieldDescriptor field = entry.getKey();
/* 281 */       Object value = entry.getValue();
/* 282 */       hash = 37 * hash + field.getNumber();
/* 283 */       if (field.isMapField()) {
/* 284 */         hash = 53 * hash + hashMapField(value); continue;
/* 285 */       }  if (field.getType() != Descriptors.FieldDescriptor.Type.ENUM) {
/* 286 */         hash = 53 * hash + value.hashCode(); continue;
/* 287 */       }  if (field.isRepeated()) {
/* 288 */         List<? extends Internal.EnumLite> list = (List<? extends Internal.EnumLite>)value;
/* 289 */         hash = 53 * hash + Internal.hashEnumList(list); continue;
/*     */       } 
/* 291 */       hash = 53 * hash + Internal.hashEnum((Internal.EnumLite)value);
/*     */     } 
/*     */     
/* 294 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   UninitializedMessageException newUninitializedMessageException() {
/* 303 */     return Builder.newUninitializedMessageException(this);
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
/*     */   public static abstract class Builder<BuilderType extends Builder<BuilderType>>
/*     */     extends AbstractMessageLite.Builder
/*     */     implements Message.Builder
/*     */   {
/*     */     public BuilderType clone() {
/* 320 */       throw new UnsupportedOperationException("clone() should be implemented in subclasses.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasOneof(Descriptors.OneofDescriptor oneof) {
/* 326 */       throw new UnsupportedOperationException("hasOneof() is not implemented.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneof) {
/* 332 */       throw new UnsupportedOperationException("getOneofFieldDescriptor() is not implemented.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType clearOneof(Descriptors.OneofDescriptor oneof) {
/* 338 */       throw new UnsupportedOperationException("clearOneof() is not implemented.");
/*     */     }
/*     */ 
/*     */     
/*     */     public BuilderType clear() {
/* 343 */       for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : getAllFields().entrySet()) {
/* 344 */         clearField(entry.getKey());
/*     */       }
/* 346 */       return (BuilderType)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> findInitializationErrors() {
/* 351 */       return MessageReflection.findMissingFields(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getInitializationErrorString() {
/* 356 */       return MessageReflection.delimitWithCommas(findInitializationErrors());
/*     */     }
/*     */ 
/*     */     
/*     */     protected BuilderType internalMergeFrom(AbstractMessageLite other) {
/* 361 */       return mergeFrom((Message)other);
/*     */     }
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(Message other) {
/* 366 */       return mergeFrom(other, other.getAllFields());
/*     */     }
/*     */     
/*     */     BuilderType mergeFrom(Message other, Map<Descriptors.FieldDescriptor, Object> allFields) {
/* 370 */       if (other.getDescriptorForType() != getDescriptorForType()) {
/* 371 */         throw new IllegalArgumentException("mergeFrom(Message) can only merge messages of the same type.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 382 */       for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
/* 383 */         Descriptors.FieldDescriptor field = entry.getKey();
/* 384 */         if (field.isRepeated()) {
/* 385 */           for (Object element : entry.getValue())
/* 386 */             addRepeatedField(field, element);  continue;
/*     */         } 
/* 388 */         if (field.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 389 */           Message existingValue = (Message)getField(field);
/* 390 */           if (existingValue == existingValue.getDefaultInstanceForType()) {
/* 391 */             setField(field, entry.getValue()); continue;
/*     */           } 
/* 393 */           setField(field, existingValue
/*     */ 
/*     */               
/* 396 */               .newBuilderForType()
/* 397 */               .mergeFrom(existingValue)
/* 398 */               .mergeFrom((Message)entry.getValue())
/* 399 */               .build());
/*     */           continue;
/*     */         } 
/* 402 */         setField(field, entry.getValue());
/*     */       } 
/*     */ 
/*     */       
/* 406 */       mergeUnknownFields(other.getUnknownFields());
/*     */       
/* 408 */       return (BuilderType)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(CodedInputStream input) throws IOException {
/* 413 */       return mergeFrom(input, ExtensionRegistry.getEmptyRegistry());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 420 */       boolean discardUnknown = input.shouldDiscardUnknownFields();
/*     */       
/* 422 */       UnknownFieldSet.Builder unknownFields = discardUnknown ? null : getUnknownFieldSetBuilder();
/* 423 */       MessageReflection.mergeMessageFrom(this, unknownFields, input, extensionRegistry);
/* 424 */       if (unknownFields != null) {
/* 425 */         setUnknownFieldSetBuilder(unknownFields);
/*     */       }
/* 427 */       return (BuilderType)this;
/*     */     }
/*     */     
/*     */     protected UnknownFieldSet.Builder getUnknownFieldSetBuilder() {
/* 431 */       return UnknownFieldSet.newBuilder(getUnknownFields());
/*     */     }
/*     */     
/*     */     protected void setUnknownFieldSetBuilder(UnknownFieldSet.Builder builder) {
/* 435 */       setUnknownFields(builder.build());
/*     */     }
/*     */ 
/*     */     
/*     */     public BuilderType mergeUnknownFields(UnknownFieldSet unknownFields) {
/* 440 */       setUnknownFields(
/* 441 */           UnknownFieldSet.newBuilder(getUnknownFields()).mergeFrom(unknownFields).build());
/* 442 */       return (BuilderType)this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Message.Builder getFieldBuilder(Descriptors.FieldDescriptor field) {
/* 447 */       throw new UnsupportedOperationException("getFieldBuilder() called on an unsupported message type.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Message.Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor field, int index) {
/* 453 */       throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on an unsupported message type.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 459 */       return TextFormat.Printer.getOutputModePrinter()
/* 460 */         .printToString(this, TextFormat.Printer.FieldReporterLevel.ABSTRACT_BUILDER_TO_STRING);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected static UninitializedMessageException newUninitializedMessageException(Message message) {
/* 466 */       return new UninitializedMessageException(MessageReflection.findMissingFields(message));
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
/*     */     void markClean() {
/* 478 */       throw new IllegalStateException("Should be overridden by subclasses.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void dispose() {
/* 489 */       throw new IllegalStateException("Should be overridden by subclasses.");
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
/*     */     public BuilderType mergeFrom(ByteString data) throws InvalidProtocolBufferException {
/* 513 */       return (BuilderType)super.mergeFrom(data);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 520 */       return (BuilderType)super.mergeFrom(data, extensionRegistry);
/*     */     }
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(byte[] data) throws InvalidProtocolBufferException {
/* 525 */       return (BuilderType)super.mergeFrom(data);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(byte[] data, int off, int len) throws InvalidProtocolBufferException {
/* 531 */       return (BuilderType)super.mergeFrom(data, off, len);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 537 */       return (BuilderType)super.mergeFrom(data, extensionRegistry);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(byte[] data, int off, int len, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
/* 547 */       return (BuilderType)super.mergeFrom(data, off, len, extensionRegistry);
/*     */     }
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(InputStream input) throws IOException {
/* 552 */       return (BuilderType)super.mergeFrom(input);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public BuilderType mergeFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 558 */       return (BuilderType)super.mergeFrom(input, extensionRegistry);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static interface BuilderParent {
/*     */     void markDirty();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\AbstractMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
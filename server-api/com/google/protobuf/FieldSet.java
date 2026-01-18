/*      */ package com.google.protobuf;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
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
/*      */ final class FieldSet<T extends FieldSet.FieldDescriptorLite<T>>
/*      */ {
/*      */   private final SmallSortedMap<T, Object> fields;
/*      */   private boolean isImmutable;
/*      */   private boolean hasLazyField;
/*      */   
/*      */   private FieldSet() {
/*   61 */     this.fields = SmallSortedMap.newFieldMap();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldSet(boolean dummy) {
/*   67 */     this(SmallSortedMap.newFieldMap());
/*   68 */     makeImmutable();
/*      */   }
/*      */   
/*      */   private FieldSet(SmallSortedMap<T, Object> fields) {
/*   72 */     this.fields = fields;
/*   73 */     makeImmutable();
/*      */   }
/*      */ 
/*      */   
/*      */   public static <T extends FieldDescriptorLite<T>> FieldSet<T> newFieldSet() {
/*   78 */     return new FieldSet<>();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends FieldDescriptorLite<T>> FieldSet<T> emptySet() {
/*   84 */     return (FieldSet)DEFAULT_INSTANCE;
/*      */   }
/*      */ 
/*      */   
/*      */   public static <T extends FieldDescriptorLite<T>> Builder<T> newBuilder() {
/*   89 */     return new Builder<>();
/*      */   }
/*      */   
/*   92 */   private static final FieldSet<?> DEFAULT_INSTANCE = new FieldSet(true);
/*      */ 
/*      */   
/*      */   boolean isEmpty() {
/*   96 */     return this.fields.isEmpty();
/*      */   }
/*      */ 
/*      */   
/*      */   public void makeImmutable() {
/*  101 */     if (this.isImmutable) {
/*      */       return;
/*      */     }
/*  104 */     int n = this.fields.getNumArrayEntries();
/*  105 */     for (int i = 0; i < n; i++) {
/*  106 */       Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
/*  107 */       Object value = entry.getValue();
/*  108 */       if (value instanceof GeneratedMessageLite) {
/*  109 */         ((GeneratedMessageLite)value).makeImmutable();
/*      */       }
/*      */     } 
/*  112 */     for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
/*  113 */       Object value = entry.getValue();
/*  114 */       if (value instanceof GeneratedMessageLite) {
/*  115 */         ((GeneratedMessageLite)value).makeImmutable();
/*      */       }
/*      */     } 
/*  118 */     this.fields.makeImmutable();
/*  119 */     this.isImmutable = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isImmutable() {
/*  129 */     return this.isImmutable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*  135 */     if (this == o) {
/*  136 */       return true;
/*      */     }
/*      */     
/*  139 */     if (!(o instanceof FieldSet)) {
/*  140 */       return false;
/*      */     }
/*      */     
/*  143 */     FieldSet<?> other = (FieldSet)o;
/*  144 */     return this.fields.equals(other.fields);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  149 */     return this.fields.hashCode();
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
/*      */   public FieldSet<T> clone() {
/*  162 */     FieldSet<T> clone = newFieldSet();
/*  163 */     int n = this.fields.getNumArrayEntries();
/*  164 */     for (int i = 0; i < n; i++) {
/*  165 */       Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
/*  166 */       clone.setField(entry.getKey(), entry.getValue());
/*      */     } 
/*  168 */     for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
/*  169 */       clone.setField(entry.getKey(), entry.getValue());
/*      */     }
/*  171 */     clone.hasLazyField = this.hasLazyField;
/*  172 */     return clone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  179 */     this.fields.clear();
/*  180 */     this.hasLazyField = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<T, Object> getAllFields() {
/*  185 */     if (this.hasLazyField) {
/*      */       
/*  187 */       SmallSortedMap<T, Object> result = cloneAllFieldsMap(this.fields, false, true);
/*  188 */       if (this.fields.isImmutable()) {
/*  189 */         result.makeImmutable();
/*      */       }
/*  191 */       return result;
/*      */     } 
/*  193 */     return this.fields.isImmutable() ? this.fields : Collections.<T, Object>unmodifiableMap(this.fields);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T extends FieldDescriptorLite<T>> SmallSortedMap<T, Object> cloneAllFieldsMap(SmallSortedMap<T, Object> fields, boolean copyList, boolean resolveLazyFields) {
/*  198 */     SmallSortedMap<T, Object> result = SmallSortedMap.newFieldMap();
/*  199 */     int n = fields.getNumArrayEntries();
/*  200 */     for (int i = 0; i < n; i++) {
/*  201 */       cloneFieldEntry(result, fields.getArrayEntryAt(i), copyList, resolveLazyFields);
/*      */     }
/*  203 */     for (Map.Entry<T, Object> entry : fields.getOverflowEntries()) {
/*  204 */       cloneFieldEntry(result, entry, copyList, resolveLazyFields);
/*      */     }
/*  206 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private static <T extends FieldDescriptorLite<T>> void cloneFieldEntry(Map<T, Object> map, Map.Entry<T, Object> entry, boolean copyList, boolean resolveLazyFields) {
/*  211 */     FieldDescriptorLite fieldDescriptorLite = (FieldDescriptorLite)entry.getKey();
/*  212 */     Object value = entry.getValue();
/*  213 */     if (resolveLazyFields && value instanceof LazyField) {
/*  214 */       map.put((T)fieldDescriptorLite, ((LazyField)value).getValue());
/*  215 */     } else if (copyList && value instanceof List) {
/*  216 */       map.put((T)fieldDescriptorLite, new ArrayList((List)value));
/*      */     } else {
/*  218 */       map.put((T)fieldDescriptorLite, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator<Map.Entry<T, Object>> iterator() {
/*  228 */     if (isEmpty()) {
/*  229 */       return Collections.emptyIterator();
/*      */     }
/*  231 */     if (this.hasLazyField) {
/*  232 */       return new LazyField.LazyIterator<>(this.fields.entrySet().iterator());
/*      */     }
/*  234 */     return this.fields.entrySet().iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Iterator<Map.Entry<T, Object>> descendingIterator() {
/*  244 */     if (isEmpty()) {
/*  245 */       return Collections.emptyIterator();
/*      */     }
/*  247 */     if (this.hasLazyField) {
/*  248 */       return new LazyField.LazyIterator<>(this.fields.descendingEntrySet().iterator());
/*      */     }
/*  250 */     return this.fields.descendingEntrySet().iterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasField(T descriptor) {
/*  255 */     if (descriptor.isRepeated()) {
/*  256 */       throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
/*      */     }
/*      */     
/*  259 */     return (this.fields.get(descriptor) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getField(T descriptor) {
/*  268 */     Object o = this.fields.get(descriptor);
/*  269 */     if (o instanceof LazyField) {
/*  270 */       return ((LazyField)o).getValue();
/*      */     }
/*  272 */     return o;
/*      */   }
/*      */ 
/*      */   
/*      */   boolean lazyFieldCorrupted(T descriptor) {
/*  277 */     Object o = this.fields.get(descriptor);
/*  278 */     return (o instanceof LazyField && ((LazyField)o).isCorrupted());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setField(T descriptor, Object<Object> value) {
/*  287 */     if (descriptor.isRepeated()) {
/*  288 */       if (!(value instanceof List)) {
/*  289 */         throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  295 */       List<?> list = (List)value;
/*  296 */       int listSize = list.size();
/*      */       
/*  298 */       List<Object> newList = new ArrayList(listSize);
/*  299 */       for (int i = 0; i < listSize; i++) {
/*  300 */         Object element = list.get(i);
/*  301 */         verifyType(descriptor, element);
/*  302 */         newList.add(element);
/*      */       } 
/*  304 */       value = (Object<Object>)newList;
/*      */     } else {
/*  306 */       verifyType(descriptor, value);
/*      */     } 
/*      */     
/*  309 */     if (value instanceof LazyField) {
/*  310 */       this.hasLazyField = true;
/*      */     }
/*  312 */     this.fields.put(descriptor, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearField(T descriptor) {
/*  317 */     this.fields.remove(descriptor);
/*  318 */     if (this.fields.isEmpty()) {
/*  319 */       this.hasLazyField = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRepeatedFieldCount(T descriptor) {
/*  325 */     if (!descriptor.isRepeated()) {
/*  326 */       throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
/*      */     }
/*      */ 
/*      */     
/*  330 */     Object value = getField(descriptor);
/*  331 */     if (value == null) {
/*  332 */       return 0;
/*      */     }
/*  334 */     return ((List)value).size();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getRepeatedField(T descriptor, int index) {
/*  340 */     if (!descriptor.isRepeated()) {
/*  341 */       throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
/*      */     }
/*      */ 
/*      */     
/*  345 */     Object value = getField(descriptor);
/*      */     
/*  347 */     if (value == null) {
/*  348 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  350 */     return ((List)value).get(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRepeatedField(T descriptor, int index, Object value) {
/*  360 */     if (!descriptor.isRepeated()) {
/*  361 */       throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
/*      */     }
/*      */ 
/*      */     
/*  365 */     Object list = getField(descriptor);
/*  366 */     if (list == null) {
/*  367 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */     
/*  370 */     verifyType(descriptor, value);
/*  371 */     ((List<Object>)list).set(index, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRepeatedField(T descriptor, Object value) {
/*      */     List<Object> list;
/*  380 */     if (!descriptor.isRepeated()) {
/*  381 */       throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
/*      */     }
/*      */ 
/*      */     
/*  385 */     verifyType(descriptor, value);
/*      */     
/*  387 */     Object existingValue = getField(descriptor);
/*      */     
/*  389 */     if (existingValue == null) {
/*  390 */       list = new ArrayList();
/*  391 */       this.fields.put(descriptor, list);
/*      */     } else {
/*  393 */       list = (List<Object>)existingValue;
/*      */     } 
/*      */     
/*  396 */     list.add(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void verifyType(T descriptor, Object value) {
/*  407 */     if (!isValidType(descriptor.getLiteType(), value)) {
/*  408 */       throw new IllegalArgumentException(
/*  409 */           String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", new Object[] {
/*      */ 
/*      */               
/*  412 */               Integer.valueOf(descriptor.getNumber()), descriptor
/*  413 */               .getLiteType().getJavaType(), value
/*  414 */               .getClass().getName()
/*      */             }));
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean isValidType(WireFormat.FieldType type, Object value) {
/*  420 */     Internal.checkNotNull(value);
/*  421 */     switch (type.getJavaType()) {
/*      */       case DOUBLE:
/*  423 */         return value instanceof Integer;
/*      */       case FLOAT:
/*  425 */         return value instanceof Long;
/*      */       case INT64:
/*  427 */         return value instanceof Float;
/*      */       case UINT64:
/*  429 */         return value instanceof Double;
/*      */       case INT32:
/*  431 */         return value instanceof Boolean;
/*      */       case FIXED64:
/*  433 */         return value instanceof String;
/*      */       case FIXED32:
/*  435 */         return (value instanceof ByteString || value instanceof byte[]);
/*      */       case BOOL:
/*  437 */         return (value instanceof Integer || value instanceof Internal.EnumLite);
/*      */       case GROUP:
/*  439 */         return (value instanceof MessageLite || value instanceof LazyField);
/*      */     } 
/*  441 */     return false;
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
/*      */   public boolean isInitialized() {
/*  453 */     int n = this.fields.getNumArrayEntries();
/*  454 */     for (int i = 0; i < n; i++) {
/*  455 */       if (!isInitialized(this.fields.getArrayEntryAt(i))) {
/*  456 */         return false;
/*      */       }
/*      */     } 
/*  459 */     for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
/*  460 */       if (!isInitialized(entry)) {
/*  461 */         return false;
/*      */       }
/*      */     } 
/*  464 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T extends FieldDescriptorLite<T>> boolean isInitialized(Map.Entry<T, Object> entry) {
/*  471 */     FieldDescriptorLite fieldDescriptorLite = (FieldDescriptorLite)entry.getKey();
/*  472 */     if (fieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
/*  473 */       if (fieldDescriptorLite.isRepeated()) {
/*  474 */         List<?> list = (List)entry.getValue();
/*  475 */         int listSize = list.size();
/*  476 */         for (int i = 0; i < listSize; i++) {
/*  477 */           Object element = list.get(i);
/*  478 */           if (!isMessageFieldValueInitialized(element)) {
/*  479 */             return false;
/*      */           }
/*      */         } 
/*      */       } else {
/*  483 */         return isMessageFieldValueInitialized(entry.getValue());
/*      */       } 
/*      */     }
/*  486 */     return true;
/*      */   }
/*      */   
/*      */   private static boolean isMessageFieldValueInitialized(Object value) {
/*  490 */     if (value instanceof MessageLiteOrBuilder)
/*      */     {
/*      */       
/*  493 */       return ((MessageLiteOrBuilder)value).isInitialized(); } 
/*  494 */     if (value instanceof LazyField) {
/*  495 */       return true;
/*      */     }
/*  497 */     throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int getWireFormatForFieldType(WireFormat.FieldType type, boolean isPacked) {
/*  508 */     if (isPacked) {
/*  509 */       return 2;
/*      */     }
/*  511 */     return type.getWireType();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void mergeFrom(FieldSet<T> other) {
/*  517 */     int n = other.fields.getNumArrayEntries();
/*  518 */     for (int i = 0; i < n; i++) {
/*  519 */       mergeFromField(other.fields.getArrayEntryAt(i));
/*      */     }
/*  521 */     for (Map.Entry<T, Object> entry : other.fields.getOverflowEntries()) {
/*  522 */       mergeFromField(entry);
/*      */     }
/*      */   }
/*      */   
/*      */   private static Object cloneIfMutable(Object value) {
/*  527 */     if (value instanceof byte[]) {
/*  528 */       byte[] bytes = (byte[])value;
/*  529 */       byte[] copy = new byte[bytes.length];
/*  530 */       System.arraycopy(bytes, 0, copy, 0, bytes.length);
/*  531 */       return copy;
/*      */     } 
/*  533 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mergeFromField(Map.Entry<T, Object> entry) {
/*  540 */     FieldDescriptorLite fieldDescriptorLite = (FieldDescriptorLite)entry.getKey();
/*  541 */     Object otherValue = entry.getValue();
/*  542 */     boolean isLazyField = otherValue instanceof LazyField;
/*      */     
/*  544 */     if (fieldDescriptorLite.isRepeated()) {
/*  545 */       if (isLazyField) {
/*  546 */         throw new IllegalStateException("Lazy fields can not be repeated");
/*      */       }
/*  548 */       Object value = getField((T)fieldDescriptorLite);
/*  549 */       List<?> otherList = (List)otherValue;
/*  550 */       int otherListSize = otherList.size();
/*  551 */       if (value == null) {
/*  552 */         value = new ArrayList(otherListSize);
/*      */       }
/*  554 */       List<Object> list = (List<Object>)value;
/*      */       
/*  556 */       for (int i = 0; i < otherListSize; i++) {
/*  557 */         Object element = otherList.get(i);
/*  558 */         list.add(cloneIfMutable(element));
/*      */       } 
/*  560 */       this.fields.put((T)fieldDescriptorLite, value);
/*  561 */     } else if (fieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
/*  562 */       Object value = getField((T)fieldDescriptorLite);
/*  563 */       if (value == null) {
/*      */         
/*  565 */         this.fields.put((T)fieldDescriptorLite, cloneIfMutable(otherValue));
/*  566 */         if (isLazyField) {
/*  567 */           this.hasLazyField = true;
/*      */         }
/*      */       } else {
/*      */         
/*  571 */         if (otherValue instanceof LazyField)
/*      */         {
/*  573 */           otherValue = ((LazyField)otherValue).getValue();
/*      */         }
/*  575 */         if (fieldDescriptorLite.internalMessageIsImmutable(value)) {
/*  576 */           MessageLite.Builder builder = ((MessageLite)value).toBuilder();
/*  577 */           fieldDescriptorLite.internalMergeFrom(builder, otherValue);
/*  578 */           this.fields.put((T)fieldDescriptorLite, builder.build());
/*      */         } else {
/*  580 */           fieldDescriptorLite.internalMergeFrom(value, otherValue);
/*      */         } 
/*      */       } 
/*      */     } else {
/*  584 */       if (isLazyField) {
/*  585 */         throw new IllegalStateException("Lazy fields must be message-valued");
/*      */       }
/*  587 */       this.fields.put((T)fieldDescriptorLite, cloneIfMutable(otherValue));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object readPrimitiveField(CodedInputStream input, WireFormat.FieldType type, boolean checkUtf8) throws IOException {
/*  604 */     if (checkUtf8) {
/*  605 */       return input.readPrimitiveField(type, WireFormat.Utf8Validation.STRICT);
/*      */     }
/*  607 */     return input.readPrimitiveField(type, WireFormat.Utf8Validation.LOOSE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeTo(CodedOutputStream output) throws IOException {
/*  613 */     int n = this.fields.getNumArrayEntries();
/*  614 */     for (int i = 0; i < n; i++) {
/*  615 */       Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
/*  616 */       writeField((FieldDescriptorLite)entry.getKey(), entry.getValue(), output);
/*      */     } 
/*  618 */     for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
/*  619 */       writeField((FieldDescriptorLite)entry.getKey(), entry.getValue(), output);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeMessageSetTo(CodedOutputStream output) throws IOException {
/*  625 */     int n = this.fields.getNumArrayEntries();
/*  626 */     for (int i = 0; i < n; i++) {
/*  627 */       writeMessageSetTo(this.fields.getArrayEntryAt(i), output);
/*      */     }
/*  629 */     for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
/*  630 */       writeMessageSetTo(entry, output);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeMessageSetTo(Map.Entry<T, Object> entry, CodedOutputStream output) throws IOException {
/*  636 */     FieldDescriptorLite<?> fieldDescriptorLite = (FieldDescriptorLite)entry.getKey();
/*  637 */     if (fieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE && 
/*  638 */       !fieldDescriptorLite.isRepeated() && 
/*  639 */       !fieldDescriptorLite.isPacked()) {
/*  640 */       Object value = entry.getValue();
/*  641 */       if (value instanceof LazyField) {
/*  642 */         ByteString valueBytes = ((LazyField)value).toByteString();
/*  643 */         output.writeRawMessageSetExtension(((FieldDescriptorLite)entry.getKey()).getNumber(), valueBytes);
/*      */       } else {
/*  645 */         output.writeMessageSetExtension(((FieldDescriptorLite)entry.getKey()).getNumber(), (MessageLite)value);
/*      */       } 
/*      */     } else {
/*  648 */       writeField(fieldDescriptorLite, entry.getValue(), output);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void writeElement(CodedOutputStream output, WireFormat.FieldType type, int number, Object value) throws IOException {
/*  669 */     if (type == WireFormat.FieldType.GROUP) {
/*  670 */       output.writeGroup(number, (MessageLite)value);
/*      */     } else {
/*  672 */       output.writeTag(number, getWireFormatForFieldType(type, false));
/*  673 */       writeElementNoTag(output, type, value);
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
/*      */ 
/*      */ 
/*      */   
/*      */   static void writeElementNoTag(CodedOutputStream output, WireFormat.FieldType type, Object value) throws IOException {
/*  688 */     switch (type) {
/*      */       case DOUBLE:
/*  690 */         output.writeDoubleNoTag(((Double)value).doubleValue());
/*      */         break;
/*      */       case FLOAT:
/*  693 */         output.writeFloatNoTag(((Float)value).floatValue());
/*      */         break;
/*      */       case INT64:
/*  696 */         output.writeInt64NoTag(((Long)value).longValue());
/*      */         break;
/*      */       case UINT64:
/*  699 */         output.writeUInt64NoTag(((Long)value).longValue());
/*      */         break;
/*      */       case INT32:
/*  702 */         output.writeInt32NoTag(((Integer)value).intValue());
/*      */         break;
/*      */       case FIXED64:
/*  705 */         output.writeFixed64NoTag(((Long)value).longValue());
/*      */         break;
/*      */       case FIXED32:
/*  708 */         output.writeFixed32NoTag(((Integer)value).intValue());
/*      */         break;
/*      */       case BOOL:
/*  711 */         output.writeBoolNoTag(((Boolean)value).booleanValue());
/*      */         break;
/*      */       case GROUP:
/*  714 */         output.writeGroupNoTag((MessageLite)value);
/*      */         break;
/*      */       case MESSAGE:
/*  717 */         output.writeMessageNoTag((MessageLite)value);
/*      */         break;
/*      */       case STRING:
/*  720 */         if (value instanceof ByteString) {
/*  721 */           output.writeBytesNoTag((ByteString)value); break;
/*      */         } 
/*  723 */         output.writeStringNoTag((String)value);
/*      */         break;
/*      */       
/*      */       case BYTES:
/*  727 */         if (value instanceof ByteString) {
/*  728 */           output.writeBytesNoTag((ByteString)value); break;
/*      */         } 
/*  730 */         output.writeByteArrayNoTag((byte[])value);
/*      */         break;
/*      */       
/*      */       case UINT32:
/*  734 */         output.writeUInt32NoTag(((Integer)value).intValue());
/*      */         break;
/*      */       case SFIXED32:
/*  737 */         output.writeSFixed32NoTag(((Integer)value).intValue());
/*      */         break;
/*      */       case SFIXED64:
/*  740 */         output.writeSFixed64NoTag(((Long)value).longValue());
/*      */         break;
/*      */       case SINT32:
/*  743 */         output.writeSInt32NoTag(((Integer)value).intValue());
/*      */         break;
/*      */       case SINT64:
/*  746 */         output.writeSInt64NoTag(((Long)value).longValue());
/*      */         break;
/*      */       
/*      */       case ENUM:
/*  750 */         if (value instanceof Internal.EnumLite) {
/*  751 */           output.writeEnumNoTag(((Internal.EnumLite)value).getNumber()); break;
/*      */         } 
/*  753 */         output.writeEnumNoTag(((Integer)value).intValue());
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void writeField(FieldDescriptorLite<?> descriptor, Object value, CodedOutputStream output) throws IOException {
/*  765 */     WireFormat.FieldType type = descriptor.getLiteType();
/*  766 */     int number = descriptor.getNumber();
/*  767 */     if (descriptor.isRepeated()) {
/*  768 */       List<?> valueList = (List)value;
/*  769 */       int valueListSize = valueList.size();
/*  770 */       if (descriptor.isPacked()) {
/*  771 */         if (valueList.isEmpty()) {
/*      */           return;
/*      */         }
/*      */         
/*  775 */         output.writeTag(number, 2);
/*      */         
/*  777 */         int dataSize = 0; int i;
/*  778 */         for (i = 0; i < valueListSize; i++) {
/*  779 */           Object element = valueList.get(i);
/*  780 */           dataSize += computeElementSizeNoTag(type, element);
/*      */         } 
/*  782 */         output.writeUInt32NoTag(dataSize);
/*      */         
/*  784 */         for (i = 0; i < valueListSize; i++) {
/*  785 */           Object element = valueList.get(i);
/*  786 */           writeElementNoTag(output, type, element);
/*      */         } 
/*      */       } else {
/*  789 */         for (int i = 0; i < valueListSize; i++) {
/*  790 */           Object element = valueList.get(i);
/*  791 */           writeElement(output, type, number, element);
/*      */         }
/*      */       
/*      */       } 
/*  795 */     } else if (value instanceof LazyField) {
/*  796 */       writeElement(output, type, number, ((LazyField)value).getValue());
/*      */     } else {
/*  798 */       writeElement(output, type, number, value);
/*      */     } 
/*      */   } public static interface FieldDescriptorLite<T extends FieldDescriptorLite<T>> extends Comparable<T> {
/*      */     int getNumber(); WireFormat.FieldType getLiteType(); WireFormat.JavaType getLiteJavaType();
/*      */     boolean isRepeated();
/*      */     boolean isPacked();
/*      */     Internal.EnumLiteMap<?> getEnumType();
/*      */     boolean internalMessageIsImmutable(Object param1Object);
/*      */     void internalMergeFrom(Object param1Object1, Object param1Object2); }
/*      */   public int getSerializedSize() {
/*  808 */     int size = 0;
/*  809 */     int n = this.fields.getNumArrayEntries();
/*  810 */     for (int i = 0; i < n; i++) {
/*  811 */       Map.Entry<T, Object> entry = this.fields.getArrayEntryAt(i);
/*  812 */       size += computeFieldSize((FieldDescriptorLite)entry.getKey(), entry.getValue());
/*      */     } 
/*  814 */     for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
/*  815 */       size += computeFieldSize((FieldDescriptorLite)entry.getKey(), entry.getValue());
/*      */     }
/*  817 */     return size;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMessageSetSerializedSize() {
/*  822 */     int size = 0;
/*  823 */     int n = this.fields.getNumArrayEntries();
/*  824 */     for (int i = 0; i < n; i++) {
/*  825 */       size += getMessageSetSerializedSize(this.fields.getArrayEntryAt(i));
/*      */     }
/*  827 */     for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
/*  828 */       size += getMessageSetSerializedSize(entry);
/*      */     }
/*  830 */     return size;
/*      */   }
/*      */   
/*      */   private int getMessageSetSerializedSize(Map.Entry<T, Object> entry) {
/*  834 */     FieldDescriptorLite<?> fieldDescriptorLite = (FieldDescriptorLite)entry.getKey();
/*  835 */     Object value = entry.getValue();
/*  836 */     if (fieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE && 
/*  837 */       !fieldDescriptorLite.isRepeated() && 
/*  838 */       !fieldDescriptorLite.isPacked()) {
/*  839 */       if (value instanceof LazyField) {
/*  840 */         return ((LazyField)value).computeMessageSetExtensionSize(((FieldDescriptorLite)entry.getKey()).getNumber());
/*      */       }
/*  842 */       return CodedOutputStream.computeMessageSetExtensionSize(((FieldDescriptorLite)entry
/*  843 */           .getKey()).getNumber(), (MessageLite)value);
/*      */     } 
/*      */     
/*  846 */     return computeFieldSize(fieldDescriptorLite, value);
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
/*      */   static int computeElementSize(WireFormat.FieldType type, int number, Object value) {
/*  861 */     int tagSize = CodedOutputStream.computeTagSize(number);
/*  862 */     if (type == WireFormat.FieldType.GROUP)
/*      */     {
/*      */       
/*  865 */       tagSize *= 2;
/*      */     }
/*  867 */     return tagSize + computeElementSizeNoTag(type, value);
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
/*      */   static int computeElementSizeNoTag(WireFormat.FieldType type, Object value) {
/*  879 */     switch (type) {
/*      */       case DOUBLE:
/*  881 */         return CodedOutputStream.computeDoubleSizeNoTag(((Double)value).doubleValue());
/*      */       case FLOAT:
/*  883 */         return CodedOutputStream.computeFloatSizeNoTag(((Float)value).floatValue());
/*      */       case INT64:
/*  885 */         return CodedOutputStream.computeInt64SizeNoTag(((Long)value).longValue());
/*      */       case UINT64:
/*  887 */         return CodedOutputStream.computeUInt64SizeNoTag(((Long)value).longValue());
/*      */       case INT32:
/*  889 */         return CodedOutputStream.computeInt32SizeNoTag(((Integer)value).intValue());
/*      */       case FIXED64:
/*  891 */         return CodedOutputStream.computeFixed64SizeNoTag(((Long)value).longValue());
/*      */       case FIXED32:
/*  893 */         return CodedOutputStream.computeFixed32SizeNoTag(((Integer)value).intValue());
/*      */       case BOOL:
/*  895 */         return CodedOutputStream.computeBoolSizeNoTag(((Boolean)value).booleanValue());
/*      */       case GROUP:
/*  897 */         return ((MessageLite)value).getSerializedSize();
/*      */       case BYTES:
/*  899 */         if (value instanceof ByteString) {
/*  900 */           return CodedOutputStream.computeBytesSizeNoTag((ByteString)value);
/*      */         }
/*  902 */         return CodedOutputStream.computeByteArraySizeNoTag((byte[])value);
/*      */       
/*      */       case STRING:
/*  905 */         if (value instanceof ByteString) {
/*  906 */           return CodedOutputStream.computeBytesSizeNoTag((ByteString)value);
/*      */         }
/*  908 */         return CodedOutputStream.computeStringSizeNoTag((String)value);
/*      */       
/*      */       case UINT32:
/*  911 */         return CodedOutputStream.computeUInt32SizeNoTag(((Integer)value).intValue());
/*      */       case SFIXED32:
/*  913 */         return CodedOutputStream.computeSFixed32SizeNoTag(((Integer)value).intValue());
/*      */       case SFIXED64:
/*  915 */         return CodedOutputStream.computeSFixed64SizeNoTag(((Long)value).longValue());
/*      */       case SINT32:
/*  917 */         return CodedOutputStream.computeSInt32SizeNoTag(((Integer)value).intValue());
/*      */       case SINT64:
/*  919 */         return CodedOutputStream.computeSInt64SizeNoTag(((Long)value).longValue());
/*      */       
/*      */       case MESSAGE:
/*  922 */         if (value instanceof LazyField) {
/*  923 */           return ((LazyField)value).computeSizeNoTag();
/*      */         }
/*  925 */         return CodedOutputStream.computeMessageSizeNoTag((MessageLite)value);
/*      */ 
/*      */       
/*      */       case ENUM:
/*  929 */         if (value instanceof Internal.EnumLite) {
/*  930 */           return CodedOutputStream.computeEnumSizeNoTag(((Internal.EnumLite)value).getNumber());
/*      */         }
/*  932 */         return CodedOutputStream.computeEnumSizeNoTag(((Integer)value).intValue());
/*      */     } 
/*      */ 
/*      */     
/*  936 */     throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int computeFieldSize(FieldDescriptorLite<?> descriptor, Object value) {
/*  943 */     WireFormat.FieldType type = descriptor.getLiteType();
/*  944 */     int number = descriptor.getNumber();
/*  945 */     if (descriptor.isRepeated()) {
/*  946 */       List<?> valueList = (List)value;
/*  947 */       int valueListSize = valueList.size();
/*  948 */       if (descriptor.isPacked()) {
/*  949 */         if (valueList.isEmpty()) {
/*  950 */           return 0;
/*      */         }
/*  952 */         int dataSize = 0;
/*  953 */         for (int j = 0; j < valueListSize; j++) {
/*  954 */           Object element = valueList.get(j);
/*  955 */           dataSize += computeElementSizeNoTag(type, element);
/*      */         } 
/*  957 */         return dataSize + 
/*  958 */           CodedOutputStream.computeTagSize(number) + 
/*  959 */           CodedOutputStream.computeUInt32SizeNoTag(dataSize);
/*      */       } 
/*  961 */       int size = 0;
/*  962 */       for (int i = 0; i < valueListSize; i++) {
/*  963 */         Object element = valueList.get(i);
/*  964 */         size += computeElementSize(type, number, element);
/*      */       } 
/*  966 */       return size;
/*      */     } 
/*      */     
/*  969 */     return computeElementSize(type, number, value);
/*      */   }
/*      */ 
/*      */   
/*      */   static final class Builder<T extends FieldDescriptorLite<T>>
/*      */   {
/*      */     private SmallSortedMap<T, Object> fields;
/*      */     
/*      */     private boolean hasLazyField;
/*      */     
/*      */     private boolean isMutable;
/*      */     
/*      */     private boolean hasNestedBuilders;
/*      */ 
/*      */     
/*      */     private Builder() {
/*  985 */       this(SmallSortedMap.newFieldMap());
/*      */     }
/*      */     
/*      */     private Builder(SmallSortedMap<T, Object> fields) {
/*  989 */       this.fields = fields;
/*  990 */       this.isMutable = true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FieldSet<T> build() {
/*  999 */       return buildImpl(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public FieldSet<T> buildPartial() {
/* 1004 */       return buildImpl(true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private FieldSet<T> buildImpl(boolean partial) {
/* 1014 */       if (this.fields.isEmpty()) {
/* 1015 */         return FieldSet.emptySet();
/*      */       }
/* 1017 */       this.isMutable = false;
/* 1018 */       SmallSortedMap<T, Object> fieldsForBuild = this.fields;
/* 1019 */       if (this.hasNestedBuilders) {
/*      */ 
/*      */         
/* 1022 */         fieldsForBuild = FieldSet.cloneAllFieldsMap(this.fields, false, false);
/* 1023 */         replaceBuilders(fieldsForBuild, partial);
/*      */       } 
/* 1025 */       FieldSet<T> fieldSet = new FieldSet<>(fieldsForBuild);
/* 1026 */       fieldSet.hasLazyField = this.hasLazyField;
/* 1027 */       return fieldSet;
/*      */     }
/*      */ 
/*      */     
/*      */     private static <T extends FieldSet.FieldDescriptorLite<T>> void replaceBuilders(SmallSortedMap<T, Object> fieldMap, boolean partial) {
/* 1032 */       int n = fieldMap.getNumArrayEntries();
/* 1033 */       for (int i = 0; i < n; i++) {
/* 1034 */         replaceBuilders(fieldMap.getArrayEntryAt(i), partial);
/*      */       }
/* 1036 */       for (Map.Entry<T, Object> entry : fieldMap.getOverflowEntries()) {
/* 1037 */         replaceBuilders(entry, partial);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private static <T extends FieldSet.FieldDescriptorLite<T>> void replaceBuilders(Map.Entry<T, Object> entry, boolean partial) {
/* 1043 */       entry.setValue(replaceBuilders((FieldSet.FieldDescriptorLite)entry.getKey(), entry.getValue(), partial));
/*      */     }
/*      */ 
/*      */     
/*      */     private static <T extends FieldSet.FieldDescriptorLite<T>> Object replaceBuilders(T descriptor, Object value, boolean partial) {
/* 1048 */       if (value == null) {
/* 1049 */         return value;
/*      */       }
/* 1051 */       if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
/* 1052 */         if (descriptor.isRepeated()) {
/* 1053 */           if (!(value instanceof List)) {
/* 1054 */             throw new IllegalStateException("Repeated field should contains a List but actually contains type: " + value
/*      */                 
/* 1056 */                 .getClass());
/*      */           }
/*      */           
/* 1059 */           List<Object> list = (List<Object>)value;
/* 1060 */           for (int i = 0; i < list.size(); i++) {
/* 1061 */             Object oldElement = list.get(i);
/* 1062 */             Object newElement = replaceBuilder(oldElement, partial);
/* 1063 */             if (newElement != oldElement) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1068 */               if (list == value) {
/* 1069 */                 list = new ArrayList(list);
/*      */               }
/* 1071 */               list.set(i, newElement);
/*      */             } 
/*      */           } 
/* 1074 */           return list;
/*      */         } 
/* 1076 */         return replaceBuilder(value, partial);
/*      */       } 
/*      */       
/* 1079 */       return value;
/*      */     }
/*      */     
/*      */     private static Object replaceBuilder(Object value, boolean partial) {
/* 1083 */       if (!(value instanceof MessageLite.Builder)) {
/* 1084 */         return value;
/*      */       }
/* 1086 */       MessageLite.Builder builder = (MessageLite.Builder)value;
/* 1087 */       if (partial) {
/* 1088 */         return builder.buildPartial();
/*      */       }
/* 1090 */       return builder.build();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static <T extends FieldSet.FieldDescriptorLite<T>> Builder<T> fromFieldSet(FieldSet<T> fieldSet) {
/* 1097 */       Builder<T> builder = new Builder<>(FieldSet.cloneAllFieldsMap(fieldSet
/* 1098 */             .fields, true, false));
/* 1099 */       builder.hasLazyField = fieldSet.hasLazyField;
/* 1100 */       return builder;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Map<T, Object> getAllFields() {
/* 1107 */       if (this.hasLazyField) {
/*      */         
/* 1109 */         SmallSortedMap<T, Object> result = FieldSet.cloneAllFieldsMap(this.fields, false, true);
/* 1110 */         if (this.fields.isImmutable()) {
/* 1111 */           result.makeImmutable();
/*      */         } else {
/* 1113 */           replaceBuilders(result, true);
/*      */         } 
/* 1115 */         return result;
/*      */       } 
/* 1117 */       return this.fields.isImmutable() ? this.fields : Collections.<T, Object>unmodifiableMap(this.fields);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasField(T descriptor) {
/* 1122 */       if (descriptor.isRepeated()) {
/* 1123 */         throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
/*      */       }
/*      */       
/* 1126 */       return (this.fields.get(descriptor) != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getField(T descriptor) {
/* 1135 */       Object value = getFieldAllowBuilders(descriptor);
/* 1136 */       return replaceBuilders(descriptor, value, true);
/*      */     }
/*      */ 
/*      */     
/*      */     Object getFieldAllowBuilders(T descriptor) {
/* 1141 */       Object o = this.fields.get(descriptor);
/* 1142 */       if (o instanceof LazyField) {
/* 1143 */         return ((LazyField)o).getValue();
/*      */       }
/* 1145 */       return o;
/*      */     }
/*      */     
/*      */     private void ensureIsMutable() {
/* 1149 */       if (!this.isMutable) {
/* 1150 */         this.fields = FieldSet.cloneAllFieldsMap(this.fields, true, false);
/* 1151 */         this.isMutable = true;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setField(T descriptor, Object<Object> value) {
/* 1162 */       ensureIsMutable();
/* 1163 */       if (descriptor.isRepeated()) {
/* 1164 */         if (!(value instanceof List)) {
/* 1165 */           throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1171 */         List<Object> newList = new ArrayList((List)value);
/* 1172 */         int newListSize = newList.size();
/* 1173 */         for (int i = 0; i < newListSize; i++) {
/* 1174 */           Object element = newList.get(i);
/* 1175 */           verifyType(descriptor, element);
/* 1176 */           this.hasNestedBuilders = (this.hasNestedBuilders || element instanceof MessageLite.Builder);
/*      */         } 
/* 1178 */         value = (Object<Object>)newList;
/*      */       } else {
/* 1180 */         verifyType(descriptor, value);
/*      */       } 
/*      */       
/* 1183 */       if (value instanceof LazyField) {
/* 1184 */         this.hasLazyField = true;
/*      */       }
/* 1186 */       this.hasNestedBuilders = (this.hasNestedBuilders || value instanceof MessageLite.Builder);
/*      */       
/* 1188 */       this.fields.put(descriptor, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clearField(T descriptor) {
/* 1193 */       ensureIsMutable();
/* 1194 */       this.fields.remove(descriptor);
/* 1195 */       if (this.fields.isEmpty()) {
/* 1196 */         this.hasLazyField = false;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getRepeatedFieldCount(T descriptor) {
/* 1204 */       if (!descriptor.isRepeated()) {
/* 1205 */         throw new IllegalArgumentException("getRepeatedFieldCount() can only be called on repeated fields.");
/*      */       }
/*      */ 
/*      */       
/* 1209 */       Object value = getFieldAllowBuilders(descriptor);
/* 1210 */       if (value == null) {
/* 1211 */         return 0;
/*      */       }
/* 1213 */       return ((List)value).size();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getRepeatedField(T descriptor, int index) {
/* 1221 */       if (this.hasNestedBuilders) {
/* 1222 */         ensureIsMutable();
/*      */       }
/* 1224 */       Object value = getRepeatedFieldAllowBuilders(descriptor, index);
/* 1225 */       return replaceBuilder(value, true);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Object getRepeatedFieldAllowBuilders(T descriptor, int index) {
/* 1233 */       if (!descriptor.isRepeated()) {
/* 1234 */         throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
/*      */       }
/*      */ 
/*      */       
/* 1238 */       Object value = getFieldAllowBuilders(descriptor);
/*      */       
/* 1240 */       if (value == null) {
/* 1241 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 1243 */       return ((List)value).get(index);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setRepeatedField(T descriptor, int index, Object value) {
/* 1253 */       ensureIsMutable();
/* 1254 */       if (!descriptor.isRepeated()) {
/* 1255 */         throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
/*      */       }
/*      */ 
/*      */       
/* 1259 */       this.hasNestedBuilders = (this.hasNestedBuilders || value instanceof MessageLite.Builder);
/*      */       
/* 1261 */       Object list = getFieldAllowBuilders(descriptor);
/* 1262 */       if (list == null) {
/* 1263 */         throw new IndexOutOfBoundsException();
/*      */       }
/*      */       
/* 1266 */       verifyType(descriptor, value);
/* 1267 */       ((List<Object>)list).set(index, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addRepeatedField(T descriptor, Object value) {
/*      */       List<Object> list;
/* 1276 */       ensureIsMutable();
/* 1277 */       if (!descriptor.isRepeated()) {
/* 1278 */         throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
/*      */       }
/*      */ 
/*      */       
/* 1282 */       this.hasNestedBuilders = (this.hasNestedBuilders || value instanceof MessageLite.Builder);
/*      */       
/* 1284 */       verifyType(descriptor, value);
/*      */       
/* 1286 */       Object existingValue = getFieldAllowBuilders(descriptor);
/*      */       
/* 1288 */       if (existingValue == null) {
/* 1289 */         list = new ArrayList();
/* 1290 */         this.fields.put(descriptor, list);
/*      */       } else {
/* 1292 */         list = (List<Object>)existingValue;
/*      */       } 
/*      */       
/* 1295 */       list.add(value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void verifyType(T descriptor, Object value) {
/* 1306 */       if (!FieldSet.isValidType(descriptor.getLiteType(), value)) {
/*      */         
/* 1308 */         if (descriptor.getLiteType().getJavaType() == WireFormat.JavaType.MESSAGE && value instanceof MessageLite.Builder) {
/*      */           return;
/*      */         }
/*      */         
/* 1312 */         throw new IllegalArgumentException(
/* 1313 */             String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", new Object[] {
/*      */ 
/*      */                 
/* 1316 */                 Integer.valueOf(descriptor.getNumber()), descriptor
/* 1317 */                 .getLiteType().getJavaType(), value
/* 1318 */                 .getClass().getName()
/*      */               }));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInitialized() {
/* 1328 */       int n = this.fields.getNumArrayEntries();
/* 1329 */       for (int i = 0; i < n; i++) {
/* 1330 */         if (!FieldSet.isInitialized(this.fields.getArrayEntryAt(i))) {
/* 1331 */           return false;
/*      */         }
/*      */       } 
/* 1334 */       for (Map.Entry<T, Object> entry : this.fields.getOverflowEntries()) {
/* 1335 */         if (!FieldSet.isInitialized(entry)) {
/* 1336 */           return false;
/*      */         }
/*      */       } 
/* 1339 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void mergeFrom(FieldSet<T> other) {
/* 1346 */       ensureIsMutable();
/* 1347 */       int n = other.fields.getNumArrayEntries();
/* 1348 */       for (int i = 0; i < n; i++) {
/* 1349 */         mergeFromField(other.fields.getArrayEntryAt(i));
/*      */       }
/* 1351 */       for (Map.Entry<T, Object> entry : (Iterable<Map.Entry<T, Object>>)other.fields.getOverflowEntries()) {
/* 1352 */         mergeFromField(entry);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void mergeFromField(Map.Entry<T, Object> entry) {
/* 1359 */       FieldSet.FieldDescriptorLite fieldDescriptorLite = (FieldSet.FieldDescriptorLite)entry.getKey();
/* 1360 */       Object otherValue = entry.getValue();
/* 1361 */       boolean isLazyField = otherValue instanceof LazyField;
/*      */       
/* 1363 */       if (fieldDescriptorLite.isRepeated()) {
/* 1364 */         if (isLazyField) {
/* 1365 */           throw new IllegalStateException("Lazy fields can not be repeated");
/*      */         }
/* 1367 */         List<Object> value = (List<Object>)getFieldAllowBuilders((T)fieldDescriptorLite);
/* 1368 */         List<?> otherList = (List)otherValue;
/* 1369 */         int otherListSize = otherList.size();
/* 1370 */         if (value == null) {
/* 1371 */           value = new ArrayList(otherListSize);
/* 1372 */           this.fields.put((T)fieldDescriptorLite, value);
/*      */         } 
/* 1374 */         for (int i = 0; i < otherListSize; i++) {
/* 1375 */           Object element = otherList.get(i);
/* 1376 */           value.add(FieldSet.cloneIfMutable(element));
/*      */         } 
/* 1378 */       } else if (fieldDescriptorLite.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
/* 1379 */         Object value = getFieldAllowBuilders((T)fieldDescriptorLite);
/* 1380 */         if (value == null) {
/*      */           
/* 1382 */           this.fields.put((T)fieldDescriptorLite, FieldSet.cloneIfMutable(otherValue));
/* 1383 */           if (isLazyField) {
/* 1384 */             this.hasLazyField = true;
/*      */           }
/*      */         } else {
/*      */           
/* 1388 */           if (otherValue instanceof LazyField)
/*      */           {
/* 1390 */             otherValue = ((LazyField)otherValue).getValue();
/*      */           }
/* 1392 */           if (fieldDescriptorLite.internalMessageIsImmutable(value)) {
/* 1393 */             MessageLite.Builder builder = ((MessageLite)value).toBuilder();
/* 1394 */             fieldDescriptorLite.internalMergeFrom(builder, otherValue);
/* 1395 */             value = builder.build();
/* 1396 */             this.fields.put((T)fieldDescriptorLite, value);
/*      */           } else {
/* 1398 */             fieldDescriptorLite.internalMergeFrom(value, otherValue);
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1402 */         if (isLazyField) {
/* 1403 */           throw new IllegalStateException("Lazy fields must be message-valued");
/*      */         }
/* 1405 */         this.fields.put((T)fieldDescriptorLite, FieldSet.cloneIfMutable(otherValue));
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\FieldSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
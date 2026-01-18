/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MapEntry<K, V>
/*     */   extends AbstractMessage
/*     */ {
/*     */   private final K key;
/*     */   private final V value;
/*     */   private final Metadata<K, V> metadata;
/*     */   private volatile int cachedSerializedSize;
/*     */   
/*     */   private static final class Metadata<K, V>
/*     */     extends MapEntryLite.Metadata<K, V>
/*     */   {
/*     */     public final Descriptors.Descriptor descriptor;
/*     */     public final Parser<MapEntry<K, V>> parser;
/*     */     
/*     */     public Metadata(Descriptors.Descriptor descriptor, MapEntry<K, V> defaultInstance, WireFormat.FieldType keyType, WireFormat.FieldType valueType) {
/*  39 */       super(keyType, defaultInstance.key, valueType, defaultInstance.value);
/*  40 */       this.descriptor = descriptor;
/*  41 */       this.parser = new AbstractParser<MapEntry<K, V>>()
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*     */           public MapEntry<K, V> parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException
/*     */           {
/*  48 */             return new MapEntry<>(MapEntry.Metadata.this, input, extensionRegistry);
/*     */           }
/*     */         };
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MapEntry(Descriptors.Descriptor descriptor, WireFormat.FieldType keyType, K defaultKey, WireFormat.FieldType valueType, V defaultValue)
/*     */   {
/* 116 */     this.cachedSerializedSize = -1; this.key = defaultKey; this.value = defaultValue; this.metadata = new Metadata<>(descriptor, this, keyType, valueType); } private MapEntry(Metadata<K, V> metadata, K key, V value) { this.cachedSerializedSize = -1; this.key = key; this.value = value; this.metadata = metadata; } private MapEntry(Metadata<K, V> metadata, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException { this.cachedSerializedSize = -1; try { this.metadata = metadata; Map.Entry<K, V> entry = MapEntryLite.parseEntry(input, metadata, extensionRegistry); this.key = entry.getKey(); this.value = entry.getValue(); }
/*     */     catch (InvalidProtocolBufferException e) { throw e.setUnfinishedMessage(this); }
/*     */     catch (IOException e)
/*     */     { throw (new InvalidProtocolBufferException(e)).setUnfinishedMessage(this); }
/* 120 */      } public int getSerializedSize() { if (this.cachedSerializedSize != -1) {
/* 121 */       return this.cachedSerializedSize;
/*     */     }
/*     */     
/* 124 */     int size = MapEntryLite.computeSerializedSize(this.metadata, this.key, this.value);
/* 125 */     this.cachedSerializedSize = size;
/* 126 */     return size; }
/*     */   public static <K, V> MapEntry<K, V> newDefaultInstance(Descriptors.Descriptor descriptor, WireFormat.FieldType keyType, K defaultKey, WireFormat.FieldType valueType, V defaultValue) { return new MapEntry<>(descriptor, keyType, defaultKey, valueType, defaultValue); }
/*     */   public K getKey() { return this.key; } public V getValue() {
/*     */     return this.value;
/*     */   } public void writeTo(CodedOutputStream output) throws IOException {
/* 131 */     MapEntryLite.writeTo(output, this.metadata, this.key, this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 136 */     return isInitialized(this.metadata, this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Parser<MapEntry<K, V>> getParserForType() {
/* 141 */     return this.metadata.parser;
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder<K, V> newBuilderForType() {
/* 146 */     return new Builder<>(this.metadata);
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder<K, V> toBuilder() {
/* 151 */     return new Builder<>(this.metadata, this.key, this.value, true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public MapEntry<K, V> getDefaultInstanceForType() {
/* 156 */     return new MapEntry(this.metadata, this.metadata.defaultKey, this.metadata.defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public Descriptors.Descriptor getDescriptorForType() {
/* 161 */     return this.metadata.descriptor;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
/* 166 */     TreeMap<Descriptors.FieldDescriptor, Object> result = new TreeMap<>();
/* 167 */     for (Descriptors.FieldDescriptor field : this.metadata.descriptor.getFields()) {
/* 168 */       if (hasField(field)) {
/* 169 */         result.put(field, getField(field));
/*     */       }
/*     */     } 
/* 172 */     return Collections.unmodifiableMap(result);
/*     */   }
/*     */   
/*     */   private void checkFieldDescriptor(Descriptors.FieldDescriptor field) {
/* 176 */     if (field.getContainingType() != this.metadata.descriptor) {
/* 177 */       throw new RuntimeException("Wrong FieldDescriptor \"" + field
/*     */           
/* 179 */           .getFullName() + "\" used in message \"" + this.metadata.descriptor
/*     */           
/* 181 */           .getFullName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasField(Descriptors.FieldDescriptor field) {
/* 187 */     checkFieldDescriptor(field);
/*     */ 
/*     */     
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getField(Descriptors.FieldDescriptor field) {
/* 195 */     checkFieldDescriptor(field);
/* 196 */     Object result = (field.getNumber() == 1) ? getKey() : getValue();
/*     */     
/* 198 */     if (field.getType() == Descriptors.FieldDescriptor.Type.ENUM) {
/* 199 */       result = field.getEnumType().findValueByNumberCreatingIfUnknown(((Integer)result).intValue());
/*     */     }
/* 201 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRepeatedFieldCount(Descriptors.FieldDescriptor field) {
/* 206 */     throw new RuntimeException("There is no repeated field in a map entry message.");
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getRepeatedField(Descriptors.FieldDescriptor field, int index) {
/* 211 */     throw new RuntimeException("There is no repeated field in a map entry message.");
/*     */   }
/*     */ 
/*     */   
/*     */   public UnknownFieldSet getUnknownFields() {
/* 216 */     return UnknownFieldSet.getDefaultInstance();
/*     */   }
/*     */   
/*     */   public static class Builder<K, V>
/*     */     extends AbstractMessage.Builder<Builder<K, V>> {
/*     */     private final MapEntry.Metadata<K, V> metadata;
/*     */     private K key;
/*     */     private V value;
/*     */     private boolean hasKey;
/*     */     private boolean hasValue;
/*     */     
/*     */     private Builder(MapEntry.Metadata<K, V> metadata) {
/* 228 */       this(metadata, metadata.defaultKey, metadata.defaultValue, false, false);
/*     */     }
/*     */     
/*     */     private Builder(MapEntry.Metadata<K, V> metadata, K key, V value, boolean hasKey, boolean hasValue) {
/* 232 */       this.metadata = metadata;
/* 233 */       this.key = key;
/* 234 */       this.value = value;
/* 235 */       this.hasKey = hasKey;
/* 236 */       this.hasValue = hasValue;
/*     */     }
/*     */     
/*     */     public K getKey() {
/* 240 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 244 */       return this.value;
/*     */     }
/*     */     
/*     */     public Builder<K, V> setKey(K key) {
/* 248 */       this.key = key;
/* 249 */       this.hasKey = true;
/* 250 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<K, V> clearKey() {
/* 254 */       this.key = this.metadata.defaultKey;
/* 255 */       this.hasKey = false;
/* 256 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<K, V> setValue(V value) {
/* 260 */       this.value = value;
/* 261 */       this.hasValue = true;
/* 262 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<K, V> clearValue() {
/* 266 */       this.value = this.metadata.defaultValue;
/* 267 */       this.hasValue = false;
/* 268 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public MapEntry<K, V> build() {
/* 273 */       MapEntry<K, V> result = buildPartial();
/* 274 */       if (!result.isInitialized()) {
/* 275 */         throw newUninitializedMessageException(result);
/*     */       }
/* 277 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public MapEntry<K, V> buildPartial() {
/* 282 */       return new MapEntry<>(this.metadata, this.key, this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public Descriptors.Descriptor getDescriptorForType() {
/* 287 */       return this.metadata.descriptor;
/*     */     }
/*     */     
/*     */     private void checkFieldDescriptor(Descriptors.FieldDescriptor field) {
/* 291 */       if (field.getContainingType() != this.metadata.descriptor) {
/* 292 */         throw new RuntimeException("Wrong FieldDescriptor \"" + field
/*     */             
/* 294 */             .getFullName() + "\" used in message \"" + this.metadata.descriptor
/*     */             
/* 296 */             .getFullName());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public Message.Builder newBuilderForField(Descriptors.FieldDescriptor field) {
/* 302 */       checkFieldDescriptor(field);
/*     */ 
/*     */ 
/*     */       
/* 306 */       if (field.getNumber() != 2 || field.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
/* 307 */         throw new RuntimeException("\"" + field.getFullName() + "\" is not a message value field.");
/*     */       }
/* 309 */       return ((Message)this.value).newBuilderForType();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> setField(Descriptors.FieldDescriptor field, Object value) {
/* 315 */       checkFieldDescriptor(field);
/* 316 */       if (value == null) {
/* 317 */         throw new NullPointerException(field.getFullName() + " is null");
/*     */       }
/*     */       
/* 320 */       if (field.getNumber() == 1) {
/* 321 */         setKey((K)value);
/*     */       } else {
/* 323 */         if (field.getType() == Descriptors.FieldDescriptor.Type.ENUM) {
/* 324 */           value = Integer.valueOf(((Descriptors.EnumValueDescriptor)value).getNumber());
/* 325 */         } else if (field.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && 
/* 326 */           !this.metadata.defaultValue.getClass().isInstance(value)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 332 */           value = ((Message)this.metadata.defaultValue).toBuilder().mergeFrom((Message)value).build();
/*     */         } 
/*     */         
/* 335 */         setValue((V)value);
/*     */       } 
/* 337 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder<K, V> clearField(Descriptors.FieldDescriptor field) {
/* 342 */       checkFieldDescriptor(field);
/* 343 */       if (field.getNumber() == 1) {
/* 344 */         clearKey();
/*     */       } else {
/* 346 */         clearValue();
/*     */       } 
/* 348 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder<K, V> setRepeatedField(Descriptors.FieldDescriptor field, int index, Object value) {
/* 353 */       throw new RuntimeException("There is no repeated field in a map entry message.");
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder<K, V> addRepeatedField(Descriptors.FieldDescriptor field, Object value) {
/* 358 */       throw new RuntimeException("There is no repeated field in a map entry message.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<K, V> setUnknownFields(UnknownFieldSet unknownFields) {
/* 364 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public MapEntry<K, V> getDefaultInstanceForType() {
/* 369 */       return new MapEntry<>(this.metadata, this.metadata.defaultKey, this.metadata.defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isInitialized() {
/* 374 */       return MapEntry.isInitialized(this.metadata, this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
/* 379 */       TreeMap<Descriptors.FieldDescriptor, Object> result = new TreeMap<>();
/* 380 */       for (Descriptors.FieldDescriptor field : this.metadata.descriptor.getFields()) {
/* 381 */         if (hasField(field)) {
/* 382 */           result.put(field, getField(field));
/*     */         }
/*     */       } 
/* 385 */       return Collections.unmodifiableMap(result);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasField(Descriptors.FieldDescriptor field) {
/* 390 */       checkFieldDescriptor(field);
/* 391 */       return (field.getNumber() == 1) ? this.hasKey : this.hasValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getField(Descriptors.FieldDescriptor field) {
/* 396 */       checkFieldDescriptor(field);
/* 397 */       Object result = (field.getNumber() == 1) ? getKey() : getValue();
/*     */       
/* 399 */       if (field.getType() == Descriptors.FieldDescriptor.Type.ENUM) {
/* 400 */         result = field.getEnumType().findValueByNumberCreatingIfUnknown(((Integer)result).intValue());
/*     */       }
/* 402 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRepeatedFieldCount(Descriptors.FieldDescriptor field) {
/* 407 */       throw new RuntimeException("There is no repeated field in a map entry message.");
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getRepeatedField(Descriptors.FieldDescriptor field, int index) {
/* 412 */       throw new RuntimeException("There is no repeated field in a map entry message.");
/*     */     }
/*     */ 
/*     */     
/*     */     public UnknownFieldSet getUnknownFields() {
/* 417 */       return UnknownFieldSet.getDefaultInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder<K, V> clone() {
/* 422 */       return new Builder(this.metadata, this.key, this.value, this.hasKey, this.hasValue);
/*     */     }
/*     */   }
/*     */   
/*     */   private static <V> boolean isInitialized(Metadata metadata, V value) {
/* 427 */     if (metadata.valueType.getJavaType() == WireFormat.JavaType.MESSAGE) {
/* 428 */       return ((MessageLite)value).isInitialized();
/*     */     }
/* 430 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   final Metadata<K, V> getMetadata() {
/* 435 */     return this.metadata;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MapEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
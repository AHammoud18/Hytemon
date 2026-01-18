/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.AbstractMap;
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
/*     */ public class MapEntryLite<K, V>
/*     */ {
/*     */   private static final int KEY_FIELD_NUMBER = 1;
/*     */   private static final int VALUE_FIELD_NUMBER = 2;
/*     */   private final Metadata<K, V> metadata;
/*     */   private final K key;
/*     */   private final V value;
/*     */   
/*     */   static class Metadata<K, V>
/*     */   {
/*     */     public final WireFormat.FieldType keyType;
/*     */     public final K defaultKey;
/*     */     public final WireFormat.FieldType valueType;
/*     */     public final V defaultValue;
/*     */     
/*     */     public Metadata(WireFormat.FieldType keyType, K defaultKey, WireFormat.FieldType valueType, V defaultValue) {
/*  35 */       this.keyType = keyType;
/*  36 */       this.defaultKey = defaultKey;
/*  37 */       this.valueType = valueType;
/*  38 */       this.defaultValue = defaultValue;
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
/*     */   private MapEntryLite(WireFormat.FieldType keyType, K defaultKey, WireFormat.FieldType valueType, V defaultValue) {
/*  52 */     this.metadata = new Metadata<>(keyType, defaultKey, valueType, defaultValue);
/*  53 */     this.key = defaultKey;
/*  54 */     this.value = defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private MapEntryLite(Metadata<K, V> metadata, K key, V value) {
/*  59 */     this.metadata = metadata;
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*     */   }
/*     */   
/*     */   public K getKey() {
/*  65 */     return this.key;
/*     */   }
/*     */   
/*     */   public V getValue() {
/*  69 */     return this.value;
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
/*     */   public static <K, V> MapEntryLite<K, V> newDefaultInstance(WireFormat.FieldType keyType, K defaultKey, WireFormat.FieldType valueType, V defaultValue) {
/*  81 */     return new MapEntryLite<>(keyType, defaultKey, valueType, defaultValue);
/*     */   }
/*     */ 
/*     */   
/*     */   static <K, V> void writeTo(CodedOutputStream output, Metadata<K, V> metadata, K key, V value) throws IOException {
/*  86 */     FieldSet.writeElement(output, metadata.keyType, 1, key);
/*  87 */     FieldSet.writeElement(output, metadata.valueType, 2, value);
/*     */   }
/*     */   
/*     */   static <K, V> int computeSerializedSize(Metadata<K, V> metadata, K key, V value) {
/*  91 */     return FieldSet.computeElementSize(metadata.keyType, 1, key) + 
/*  92 */       FieldSet.computeElementSize(metadata.valueType, 2, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T parseField(CodedInputStream input, ExtensionRegistryLite extensionRegistry, WireFormat.FieldType type, T value) throws IOException {
/*     */     MessageLite.Builder subBuilder;
/* 102 */     switch (type) {
/*     */       case MESSAGE:
/* 104 */         subBuilder = ((MessageLite)value).toBuilder();
/* 105 */         input.readMessage(subBuilder, extensionRegistry);
/* 106 */         return (T)subBuilder.buildPartial();
/*     */       case ENUM:
/* 108 */         return (T)Integer.valueOf(input.readEnum());
/*     */       case GROUP:
/* 110 */         throw new RuntimeException("Groups are not allowed in maps.");
/*     */     } 
/* 112 */     return (T)FieldSet.readPrimitiveField(input, type, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serializeTo(CodedOutputStream output, int fieldNumber, K key, V value) throws IOException {
/* 123 */     output.writeTag(fieldNumber, 2);
/* 124 */     output.writeUInt32NoTag(computeSerializedSize(this.metadata, key, value));
/* 125 */     writeTo(output, this.metadata, key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeMessageSize(int fieldNumber, K key, V value) {
/* 134 */     return CodedOutputStream.computeTagSize(fieldNumber) + 
/* 135 */       CodedOutputStream.computeLengthDelimitedFieldSize(
/* 136 */         computeSerializedSize(this.metadata, key, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map.Entry<K, V> parseEntry(ByteString bytes, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 145 */     return parseEntry(bytes.newCodedInput(), this.metadata, extensionRegistry);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static <K, V> Map.Entry<K, V> parseEntry(CodedInputStream input, Metadata<K, V> metadata, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 151 */     K key = metadata.defaultKey;
/* 152 */     V value = metadata.defaultValue;
/*     */     while (true) {
/* 154 */       int tag = input.readTag();
/* 155 */       if (tag == 0) {
/*     */         break;
/*     */       }
/* 158 */       if (tag == WireFormat.makeTag(1, metadata.keyType.getWireType())) {
/* 159 */         key = parseField(input, extensionRegistry, metadata.keyType, key); continue;
/* 160 */       }  if (tag == WireFormat.makeTag(2, metadata.valueType.getWireType())) {
/* 161 */         value = parseField(input, extensionRegistry, metadata.valueType, value); continue;
/*     */       } 
/* 163 */       if (!input.skipField(tag)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 168 */     return new AbstractMap.SimpleImmutableEntry<>(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void parseInto(MapFieldLite<K, V> map, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
/* 178 */     int length = input.readRawVarint32();
/* 179 */     int oldLimit = input.pushLimit(length);
/* 180 */     K key = this.metadata.defaultKey;
/* 181 */     V value = this.metadata.defaultValue;
/*     */     
/*     */     while (true) {
/* 184 */       int tag = input.readTag();
/* 185 */       if (tag == 0) {
/*     */         break;
/*     */       }
/* 188 */       if (tag == WireFormat.makeTag(1, this.metadata.keyType.getWireType())) {
/* 189 */         key = parseField(input, extensionRegistry, this.metadata.keyType, key); continue;
/* 190 */       }  if (tag == WireFormat.makeTag(2, this.metadata.valueType.getWireType())) {
/* 191 */         value = parseField(input, extensionRegistry, this.metadata.valueType, value); continue;
/*     */       } 
/* 193 */       if (!input.skipField(tag)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 199 */     input.checkLastTagWas(0);
/* 200 */     input.popLimit(oldLimit);
/* 201 */     map.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   Metadata<K, V> getMetadata() {
/* 206 */     return this.metadata;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MapEntryLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
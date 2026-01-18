/*    */ package com.google.protobuf;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CheckReturnValue
/*    */ final class MapFieldSchemaLite
/*    */   implements MapFieldSchema
/*    */ {
/*    */   public Map<?, ?> forMutableMapData(Object mapField) {
/* 18 */     return (MapFieldLite)mapField;
/*    */   }
/*    */ 
/*    */   
/*    */   public MapEntryLite.Metadata<?, ?> forMapMetadata(Object mapDefaultEntry) {
/* 23 */     return ((MapEntryLite<?, ?>)mapDefaultEntry).getMetadata();
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<?, ?> forMapData(Object mapField) {
/* 28 */     return (MapFieldLite)mapField;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isImmutable(Object mapField) {
/* 33 */     return !((MapFieldLite)mapField).isMutable();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object toImmutable(Object mapField) {
/* 38 */     ((MapFieldLite)mapField).makeImmutable();
/* 39 */     return mapField;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object newMapField(Object unused) {
/* 44 */     return MapFieldLite.emptyMapField().mutableCopy();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object mergeFrom(Object destMapField, Object srcMapField) {
/* 49 */     return mergeFromLite(destMapField, srcMapField);
/*    */   }
/*    */ 
/*    */   
/*    */   private static <K, V> MapFieldLite<K, V> mergeFromLite(Object destMapField, Object srcMapField) {
/* 54 */     MapFieldLite<K, V> mine = (MapFieldLite<K, V>)destMapField;
/* 55 */     MapFieldLite<K, V> other = (MapFieldLite<K, V>)srcMapField;
/* 56 */     if (!other.isEmpty()) {
/* 57 */       if (!mine.isMutable()) {
/* 58 */         mine = mine.mutableCopy();
/*    */       }
/* 60 */       mine.mergeFrom(other);
/*    */     } 
/* 62 */     return mine;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize(int fieldNumber, Object mapField, Object mapDefaultEntry) {
/* 67 */     return getSerializedSizeLite(fieldNumber, mapField, mapDefaultEntry);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static <K, V> int getSerializedSizeLite(int fieldNumber, Object mapField, Object defaultEntry) {
/* 73 */     MapFieldLite<K, V> mapFieldLite = (MapFieldLite<K, V>)mapField;
/* 74 */     MapEntryLite<K, V> defaultEntryLite = (MapEntryLite<K, V>)defaultEntry;
/*    */     
/* 76 */     if (mapFieldLite.isEmpty()) {
/* 77 */       return 0;
/*    */     }
/* 79 */     int size = 0;
/* 80 */     for (Map.Entry<K, V> entry : mapFieldLite.entrySet()) {
/* 81 */       size += defaultEntryLite.computeMessageSize(fieldNumber, entry.getKey(), entry.getValue());
/*    */     }
/* 83 */     return size;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MapFieldSchemaLite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
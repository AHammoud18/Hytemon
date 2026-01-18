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
/*    */ final class MapFieldSchemaFull
/*    */   implements MapFieldSchema
/*    */ {
/*    */   public Map<?, ?> forMutableMapData(Object mapField) {
/* 16 */     return ((MapField<?, ?>)mapField).getMutableMap();
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<?, ?> forMapData(Object mapField) {
/* 21 */     return ((MapField<?, ?>)mapField).getMap();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isImmutable(Object mapField) {
/* 26 */     return !((MapField)mapField).isMutable();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object toImmutable(Object mapField) {
/* 31 */     ((MapField)mapField).makeImmutable();
/* 32 */     return mapField;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object newMapField(Object mapDefaultEntry) {
/* 37 */     return MapField.newMapField((MapEntry<?, ?>)mapDefaultEntry);
/*    */   }
/*    */ 
/*    */   
/*    */   public MapEntryLite.Metadata<?, ?> forMapMetadata(Object mapDefaultEntry) {
/* 42 */     return ((MapEntry<?, ?>)mapDefaultEntry).getMetadata();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object mergeFrom(Object destMapField, Object srcMapField) {
/* 47 */     return mergeFromFull(destMapField, srcMapField);
/*    */   }
/*    */ 
/*    */   
/*    */   private static <K, V> Object mergeFromFull(Object destMapField, Object srcMapField) {
/* 52 */     MapField<K, V> mine = (MapField<K, V>)destMapField;
/* 53 */     MapField<K, V> other = (MapField<K, V>)srcMapField;
/* 54 */     if (!mine.isMutable()) {
/* 55 */       mine.copy();
/*    */     }
/* 57 */     mine.mergeFrom(other);
/* 58 */     return mine;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize(int number, Object mapField, Object mapDefaultEntry) {
/* 63 */     return getSerializedSizeFull(number, mapField, mapDefaultEntry);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static <K, V> int getSerializedSizeFull(int number, Object mapField, Object defaultEntryObject) {
/* 70 */     if (mapField == null) {
/* 71 */       return 0;
/*    */     }
/*    */     
/* 74 */     Map<K, V> map = ((MapField<K, V>)mapField).getMap();
/* 75 */     MapEntry<K, V> defaultEntry = (MapEntry<K, V>)defaultEntryObject;
/* 76 */     if (map.isEmpty()) {
/* 77 */       return 0;
/*    */     }
/* 79 */     int size = 0;
/* 80 */     for (Map.Entry<K, V> entry : map.entrySet()) {
/* 81 */       size += 
/* 82 */         CodedOutputStream.computeTagSize(number) + 
/* 83 */         CodedOutputStream.computeLengthDelimitedFieldSize(
/* 84 */           MapEntryLite.computeSerializedSize(defaultEntry
/* 85 */             .getMetadata(), entry.getKey(), entry.getValue()));
/*    */     }
/* 87 */     return size;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MapFieldSchemaFull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
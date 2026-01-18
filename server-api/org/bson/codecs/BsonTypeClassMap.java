/*     */ package org.bson.codecs;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.bson.BsonDbPointer;
/*     */ import org.bson.BsonRegularExpression;
/*     */ import org.bson.BsonTimestamp;
/*     */ import org.bson.BsonType;
/*     */ import org.bson.BsonUndefined;
/*     */ import org.bson.Document;
/*     */ import org.bson.types.Binary;
/*     */ import org.bson.types.Code;
/*     */ import org.bson.types.CodeWithScope;
/*     */ import org.bson.types.Decimal128;
/*     */ import org.bson.types.MaxKey;
/*     */ import org.bson.types.MinKey;
/*     */ import org.bson.types.ObjectId;
/*     */ import org.bson.types.Symbol;
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
/*     */ public class BsonTypeClassMap
/*     */ {
/*  73 */   static final BsonTypeClassMap DEFAULT_BSON_TYPE_CLASS_MAP = new BsonTypeClassMap();
/*  74 */   private final Map<BsonType, Class<?>> map = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonTypeClassMap(Map<BsonType, Class<?>> replacementsForDefaults) {
/*  83 */     addDefaults();
/*  84 */     this.map.putAll(replacementsForDefaults);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BsonTypeClassMap() {
/*  91 */     this(Collections.emptyMap());
/*     */   }
/*     */   
/*     */   Set<BsonType> keys() {
/*  95 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> get(BsonType bsonType) {
/* 105 */     return this.map.get(bsonType);
/*     */   }
/*     */   
/*     */   private void addDefaults() {
/* 109 */     this.map.put(BsonType.ARRAY, List.class);
/* 110 */     this.map.put(BsonType.BINARY, Binary.class);
/* 111 */     this.map.put(BsonType.BOOLEAN, Boolean.class);
/* 112 */     this.map.put(BsonType.DATE_TIME, Date.class);
/* 113 */     this.map.put(BsonType.DB_POINTER, BsonDbPointer.class);
/* 114 */     this.map.put(BsonType.DOCUMENT, Document.class);
/* 115 */     this.map.put(BsonType.DOUBLE, Double.class);
/* 116 */     this.map.put(BsonType.INT32, Integer.class);
/* 117 */     this.map.put(BsonType.INT64, Long.class);
/* 118 */     this.map.put(BsonType.DECIMAL128, Decimal128.class);
/* 119 */     this.map.put(BsonType.MAX_KEY, MaxKey.class);
/* 120 */     this.map.put(BsonType.MIN_KEY, MinKey.class);
/* 121 */     this.map.put(BsonType.JAVASCRIPT, Code.class);
/* 122 */     this.map.put(BsonType.JAVASCRIPT_WITH_SCOPE, CodeWithScope.class);
/* 123 */     this.map.put(BsonType.OBJECT_ID, ObjectId.class);
/* 124 */     this.map.put(BsonType.REGULAR_EXPRESSION, BsonRegularExpression.class);
/* 125 */     this.map.put(BsonType.STRING, String.class);
/* 126 */     this.map.put(BsonType.SYMBOL, Symbol.class);
/* 127 */     this.map.put(BsonType.TIMESTAMP, BsonTimestamp.class);
/* 128 */     this.map.put(BsonType.UNDEFINED, BsonUndefined.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 133 */     if (this == o) {
/* 134 */       return true;
/*     */     }
/* 136 */     if (o == null || getClass() != o.getClass()) {
/* 137 */       return false;
/*     */     }
/*     */     
/* 140 */     BsonTypeClassMap that = (BsonTypeClassMap)o;
/*     */     
/* 142 */     if (!this.map.equals(that.map)) {
/* 143 */       return false;
/*     */     }
/*     */     
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 151 */     return this.map.hashCode();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\bson\codecs\BsonTypeClassMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */